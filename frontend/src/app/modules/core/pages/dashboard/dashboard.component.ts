import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ISubscription } from 'rxjs/Subscription';
import { Chart } from 'chart.js';
import * as moment from 'moment';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/retry';

import { SystemVariables } from '@config/system-variables';
import { DashboardService } from '@modules/core/pages/dashboard/service/dashboard.service';
import { MonthSummary } from '@modules/core/pages/dashboard/domain/month-summary';
import { ChartData } from '@modules/core/pages/dashboard/domain/chart-data';
import { TotalExpenditure } from '@modules/core/pages/dashboard/domain/total-expenditure';
import { ChartConfig } from '@modules/core/pages/dashboard/constants/chart-config';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { Subject } from '@shared/domain/subject/subject';
import { Role } from '@shared/domain/subject/role';
import { Month } from '@shared/constants/enumeration/month';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [DashboardService, SubjectDetailsService, JwtHelperService],
})
export class DashboardComponent implements OnInit, AfterViewInit, OnDestroy {
  private $dashboardService: ISubscription;
  public currentYear = moment().year();
  public role: Role;
  public subject: Subject;
  public dataSource: MatTableDataSource<Subject> = new MatTableDataSource<Subject>();
  public displayedColumns: Array<string> = ['firstName', 'lastName', 'position'];
  public isFetching: boolean;
  public allowanceLeft: number;
  public totalDelegationExpenditure: TotalExpenditure;

  @ViewChild('monthlySummariesChart')
  public monthlySummariesCanvas: ElementRef;

  @ViewChild(MatPaginator)
  public paginator: MatPaginator;

  constructor(
    private _dashboardService: DashboardService,
    private _subjectService: SubjectDetailsService,
    private _jwtHelper: JwtHelperService,
    private _errorResolver: ErrorResolverService
  ) {}

  public static splitMonthlySummaries(monthlySummaries: Array<MonthSummary>): ChartData {
    const monthLabels: Array<Month> = monthlySummaries.map((element: MonthSummary) => {
      return element.month;
    });
    const dataSet: Array<number> = monthlySummaries.map((element: MonthSummary) => {
      return element.numberOfApplications;
    });
    return {
      labels: monthLabels,
      data: dataSet,
    };
  }

  public ngOnInit(): void {
    this.role = this._jwtHelper.getUsersRole() ? this._jwtHelper.getUsersRole()[0] : null;
    this.fetchChartsData();
  }

  public ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  public ngOnDestroy(): void {
    if (this.$dashboardService !== undefined) {
      this.$dashboardService.unsubscribe();
    }
  }

  public fetchChartsData(): void {
    this.isFetching = true;
    this.$dashboardService = Observable.zip(
      this._subjectService.getCurrentSubject(),
      this._dashboardService.getSubjectsOnLeave(),
      this._dashboardService.getMonthlyReport(),
      this._dashboardService.getTotalDelegationExpenditures(),
      (subject: Subject, subjectsOnLeave: Array<Subject>, monthlyReport: Array<MonthSummary>, totalExpenditure: TotalExpenditure) => ({
        subject,
        subjectsOnLeave,
        monthlyReport,
        totalExpenditure,
      })
    )
      .retry(SystemVariables.RETRY_TIMES)
      .finally(() => (this.isFetching = false))
      .subscribe(
        pair => {
          this.subject = pair.subject;
          this.dataSource.data = pair.subjectsOnLeave;
          this.totalDelegationExpenditure = pair.totalExpenditure;
          this.buildMonthlySummariesChart(DashboardComponent.splitMonthlySummaries(pair.monthlyReport));
          this.setAllowanceInfo(pair.subject);
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
  }

  public setAllowanceInfo(subject: Subject): void {
    this.allowanceLeft = subject.hrInformation.allowance - subject.hrInformation.usedAllowance;
  }

  private buildMonthlySummariesChart(chartData: ChartData): void {
    this.monthlySummariesCanvas = new Chart(this.monthlySummariesCanvas.nativeElement.getContext('2d'), {
      type: 'line',
      data: {
        labels: chartData.labels,
        datasets: [
          {
            data: chartData.data,
            borderColor: '#3CBA9F',
            fill: false,
          },
        ],
      },
      options: ChartConfig.chartOptions,
    });
  }
}
