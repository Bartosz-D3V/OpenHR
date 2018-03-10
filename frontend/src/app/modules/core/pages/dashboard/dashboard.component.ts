import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ISubscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import { Chart } from 'chart.js';

import { DashboardService } from '@modules/core/pages/dashboard/service/dashboard.service';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { MonthSummary } from '@modules/core/pages/dashboard/domain/month-summary';
import { Subject } from '@shared/domain/subject/subject';
import { Month } from '@shared/constants/enumeration/month';
import { ChartData } from '@modules/core/pages/dashboard/domain/chart-data';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { LeaveApplication } from '@shared/domain/leave-application/leave-application';

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
  private $monthlySummary: ISubscription;
  private $subjectsOnLeave: ISubscription;
  public subject: Subject;
  public dataSource: MatTableDataSource<Subject> = new MatTableDataSource<Subject>();
  public displayedColumns: Array<string> = ['firstName', 'lastName', 'position'];
  public isLoadingResults: boolean;
  public usedAllowance: number;
  public allowanceLeft: number;

  @ViewChild('monthlySummariesChart')
  public monthlySummariesCanvas: ElementRef;

  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  constructor(private _dashboardService: DashboardService,
              private _subjectService: SubjectDetailsService,
              private _errorResolver: ErrorResolverService) {
  }

  ngOnInit(): void {
    this.isLoadingResults = true;
    this.fetchChartsData();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.createMonthlySummaryChart();
  }

  ngOnDestroy(): void {
    this.unsubscribeAll();
  }

  public createMonthlySummaryChart(): void {
    this.$monthlySummary = this._dashboardService
      .getMonthlyReport()
      .subscribe((val: Array<MonthSummary>) => {
        const chartData: ChartData = this.splitArrayByFields(val);
        this.buildMonthlySummariesChart(chartData);
      }, (err: any) => {
        this._errorResolver.handleError(err);
      });
  }

  public fetchChartsData(): void {
    this.$dashboardService = Observable.zip(
      this._subjectService.getCurrentSubject(),
      this._dashboardService.getSubjectsOnLeave(),
      (subject: Subject, subjectsOnLeave: Array<Subject>) => ({subject, subjectsOnLeave})
    ).subscribe((pair) => {
      this.subject = pair.subject;
      this.dataSource.data = pair.subjectsOnLeave;
      this.isLoadingResults = false;
      this.buildCharts(pair.subject);
    }, (err: any) => {
      this._errorResolver.handleError(err);
    });
  }

  public buildCharts(subject: Subject): void {
    this.allowanceLeft = subject.hrInformation.allowance - subject.hrInformation.usedAllowance;
    this.usedAllowance = this.convertUsedAllowanceToRatio(subject.hrInformation.allowance,
      subject.hrInformation.usedAllowance);
  }

  public convertUsedAllowanceToRatio(allowance: number, usedAllowance: number): number {
    return ((usedAllowance / allowance) * 100);
  }

  public splitArrayByFields(monthlySummaries: Array<MonthSummary>): ChartData {
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

  private buildMonthlySummariesChart(chartData: ChartData) {
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
      options: {
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
      },
    });
  }

  private unsubscribeAll(): void {
    if (this.$dashboardService !== undefined) {
      this.$dashboardService.unsubscribe();
    }
    if (this.$monthlySummary !== undefined) {
      this.$monthlySummary.unsubscribe();
    }
    if (this.$subjectsOnLeave !== undefined) {
      this.$subjectsOnLeave.unsubscribe();
    }
  }
}
