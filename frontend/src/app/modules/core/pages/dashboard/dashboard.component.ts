import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ISubscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import { Chart } from 'chart.js';
import { MatPaginator, MatTableDataSource } from '@angular/material';

import { DashboardService } from '@modules/core/pages/dashboard/service/dashboard.service';
import { MonthSummary } from '@modules/core/pages/dashboard/domain/month-summary';
import { ChartData } from '@modules/core/pages/dashboard/domain/chart-data';
import { ApplicationsStatusRadio } from '@modules/core/pages/dashboard/domain/applications-status-radio';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Subject } from '@shared/domain/subject/subject';
import { Month } from '@shared/constants/enumeration/month';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [
    DashboardService,
    SubjectDetailsService,
  ],
})
export class DashboardComponent implements OnInit, AfterViewInit, OnDestroy {
  private $dashboardService: ISubscription;
  private chartOptions: Object;
  public subject: Subject;
  public dataSource: MatTableDataSource<Subject> = new MatTableDataSource<Subject>();
  public displayedColumns: Array<string> = ['firstName', 'lastName', 'position'];
  public isLoadingResults: boolean;
  public allowanceLeft: number;

  @ViewChild('monthlySummariesChart')
  public monthlySummariesCanvas: ElementRef;

  @ViewChild('applicationsStatusRatioChart')
  public applicationsStatusRatioCanvas: ElementRef;

  @ViewChild(MatPaginator)
  public paginator: MatPaginator;

  constructor(private _dashboardService: DashboardService,
              private _subjectService: SubjectDetailsService,
              private _errorResolver: ErrorResolverService) {
  }

  public static splitMonthlySummaries(monthlySummaries: Array<MonthSummary>): ChartData {
    const monthLabels: Array<Month> = monthlySummaries
      .map((element: MonthSummary) => {
        return element.month;
      });
    const dataSet: Array<number> = monthlySummaries
      .map((element: MonthSummary) => {
        return element.numberOfApplications;
      });
    return {
      labels: monthLabels,
      data: dataSet,
    };
  }

  ngOnInit(): void {
    this.isLoadingResults = true;
    this.chartOptions = this.getChartOptions();
    this.fetchChartsData();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.createMonthlySummaryChart();
    this.createApplicationsStatusRatioChart();
  }

  ngOnDestroy(): void {
    if (this.$dashboardService !== undefined) {
      this.$dashboardService.unsubscribe();
    }
  }

  public createMonthlySummaryChart(): void {
    this.$dashboardService = this._dashboardService
      .getMonthlyReport()
      .subscribe((val: Array<MonthSummary>) => {
        const chartData: ChartData = DashboardComponent.splitMonthlySummaries(val);
        this.buildMonthlySummariesChart(chartData);
      }, (err: any) => {
        this._errorResolver.handleError(err);
      });
  }

  public createApplicationsStatusRatioChart(): void {
    this.$dashboardService = this._dashboardService
      .getApplicationsStatusRatio()
      .subscribe((val: ApplicationsStatusRadio) => {
        this.buildApplicationsStatusRatio(val);
      }, (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      });
  }

  public fetchChartsData(): void {
    this.$dashboardService = Observable.zip(
      this._subjectService.getCurrentSubject(),
      this._dashboardService.getSubjectsOnLeave(),
      (subject: Subject, subjectsOnLeave: Array<Subject>) =>
        ({subject, subjectsOnLeave})
    ).subscribe((pair) => {
      this.subject = pair.subject;
      this.dataSource.data = pair.subjectsOnLeave;
      this.isLoadingResults = false;
      this.setAllowanceInfo(pair.subject);
    }, (httpErrorResponse: HttpErrorResponse) => {
      this._errorResolver.handleError(httpErrorResponse.error);
    });
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
            borderColor: '#3cba9f',
            fill: false,
          },
        ],
      },
      options: this.chartOptions,
    });
  }

  private buildApplicationsStatusRatio(chartData: ApplicationsStatusRadio): void {
    this.applicationsStatusRatioCanvas = new Chart(this.applicationsStatusRatioCanvas.nativeElement.getContext('2d'), {
      type: 'bar',
      data: {
        labels: ['Accepted', 'Rejected'],
        datasets: [
          {
            data: [chartData.accepted, chartData.rejected],
            borderColor: '#3cba9f',
            fill: true,
          },
        ],
      },
      options: this.chartOptions,
    });
  }

  private getChartOptions(): Object {
    return {
      legend: {
        display: false,
      },
      scales: {
        xAxes: [{
          display: true,
          ticks: {
            beginAtZero: true,
          },
        }],
        yAxes: [{
          display: true,
          ticks: {
            beginAtZero: true,
            fixedStepSize: 1,
            userCallback: (label) => {
              return Math.floor(label);
            },
          },
        }],
      },
    };
  }
}
