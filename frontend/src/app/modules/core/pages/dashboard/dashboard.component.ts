import { Component, OnDestroy, OnInit } from '@angular/core';
import { ISubscription } from 'rxjs/Subscription';

import { Subject } from '../../../../shared/domain/subject/subject';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
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
      }, (err: any) => {
        this._errorResolver.handleError(err);
      });
  }
}
