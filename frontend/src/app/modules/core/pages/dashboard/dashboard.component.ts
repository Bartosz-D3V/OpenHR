import { Component, OnDestroy, OnInit } from '@angular/core';
import { ISubscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';

import { DashboardService } from '@modules/core/pages/dashboard/service/dashboard.service';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { MonthSummary } from '@modules/core/pages/dashboard/domain/month-summary';
import { Subject } from '@shared/domain/subject/subject';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [
    DashboardService,
    SubjectDetailsService,
  ],
})
export class DashboardComponent implements OnInit, OnDestroy {
  private $dashboardService: ISubscription;
  public monthlySummaries: Array<MonthSummary>;
  public subject: Subject;
  public usedAllowance: number;
  public allowanceLeft: number;

  constructor(private _dashboardService: DashboardService,
              private _subjectService: SubjectDetailsService,
              private _errorResolver: ErrorResolverService) {
  }

  ngOnInit() {
    this.fetchChartsData();
  }

  ngOnDestroy(): void {
    if (this.$dashboardService !== undefined) {
      this.$dashboardService.unsubscribe();
    }
  }

  public fetchChartsData(): void {
    this.$dashboardService = Observable.zip(
      this._subjectService.getCurrentSubject(),
      this._dashboardService.getMonthlyReport(),
      (subject: Subject, monthlySummaries: Array<MonthSummary>) => ({subject, monthlySummaries})
    ).subscribe((pair) => {
      this.subject = pair.subject;
      this.buildCharts(pair.subject);
      this.monthlySummaries = pair.monthlySummaries;
    }, (err: any) => {
      this._errorResolver.handleError(err);
    });
  }

  public buildCharts(subject: Subject): void {
    this.allowanceLeft = subject.hrInformation.allowance - subject.hrInformation.usedAllowance;
    this.usedAllowance = this.convertUsedAllowanceToPercent(subject.hrInformation.allowance,
      subject.hrInformation.usedAllowance);
  }

  private convertUsedAllowanceToPercent(allowance: number, usedAllowance: number): number {
    return ((usedAllowance / allowance) * 100);
  }
}
