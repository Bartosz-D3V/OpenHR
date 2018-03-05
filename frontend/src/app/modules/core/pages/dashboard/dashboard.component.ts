import { Component, OnDestroy, OnInit } from '@angular/core';
import { ISubscription } from 'rxjs/Subscription';

import { Subject } from '../../../../shared/domain/subject/subject';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { HrInformation } from '../../../../shared/domain/subject/hr-information';
import { SingleChartData } from './domain/single-chart-data';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [
    SubjectDetailsService,
  ],
})
export class DashboardComponent implements OnInit, OnDestroy {
  private $subject: ISubscription;
  private subject: Subject;
  public allowanceData: Array<SingleChartData> = [];

  constructor(private _subjectService: SubjectDetailsService,
              private _errorResolver: ErrorResolverService) {
  }

  ngOnInit() {
    this.fetchCurrentSubject();
  }

  ngOnDestroy(): void {
    if (this.$subject !== undefined) {
      this.$subject.unsubscribe();
    }
  }

  public fetchCurrentSubject(): void {
    this.$subject = this._subjectService
      .getCurrentSubject()
      .subscribe((val: Subject) => {
        this.subject = val;
        this.buildCharts(val);
      }, (err: any) => {
        this._errorResolver.handleError(err);
      });
  }

  public buildCharts(subject: Subject): void {
    this.allowanceData = this.prepareAllowanceData(subject.hrInformation);
  }

  public prepareAllowanceData(hrInformation: HrInformation): Array<SingleChartData> {
    const allowanceDataOne: SingleChartData = {
      'name': 'Total allowance',
      'value': hrInformation.allowance,
    };
    const allowanceDataTwo: SingleChartData = {
      'name': 'Used allowance',
      'value': hrInformation.usedAllowance,
    };
    return [allowanceDataOne, allowanceDataTwo];
  }
}
