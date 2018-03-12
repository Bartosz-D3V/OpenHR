import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs/Observable';
import { ISubscription } from 'rxjs/Subscription';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/filter';

import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { Subject } from '@shared/domain/subject/subject';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';

@Component({
  selector: 'app-delegation',
  templateUrl: './delegation.component.html',
  styleUrls: ['./delegation.component.scss'],
  providers: [SubjectDetailsService],
})
export class DelegationComponent implements OnInit, OnDestroy {
  private $currentSubject: ISubscription;
  public subject: Subject;
  public isLoadingResults: boolean;
  public applicationForm: FormGroup;
  public filteredCountries: Observable<Array<string>>;
  public countries: Array<string>;

  constructor(private _subjectDetails: SubjectDetailsService,
              private _errorResolver: ErrorResolverService,
              private _fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.getCurrentSubject();
  }

  ngOnDestroy(): void {
    if (this.$currentSubject !== undefined) {
      this.$currentSubject.unsubscribe();
    }
  }

  public constructForm(): void {
    this.applicationForm = this._fb.group({
      name: this._fb.group({
        subjectId: [this.subject.subjectId, [
          Validators.required,
          Validators.pattern(RegularExpressions.NUMBERS_ONLY),
        ]],
        first: [this.subject.personalInformation.firstName,
          Validators.required,
        ],
        middle: [this.subject.personalInformation.middleName],
        last: [this.subject.personalInformation.lastName,
          Validators.required,
        ],
      }),
      organisation: this._fb.group({
        position: [this.subject.employeeInformation.position,
          Validators.required,
        ],
        department: [this.subject.employeeInformation.department],
      }),
      delegation: this._fb.group({
        country: [''],
        city: [''],
        objective: ['', Validators.required],
        budget: [0, [
          Validators.required,
          Validators.min(0),
        ]],
      }),
    });

    this.filteredCountries = this.reduceCountries(this.countries);
    this.applicationForm.get('name').disable();
    this.applicationForm.get('organisation').disable();
  }

  public getCurrentSubject(): void {
    this.isLoadingResults = true;
    this.$currentSubject = this._subjectDetails
      .getCurrentSubject()
      .subscribe((response: Subject) => {
        this.subject = response;
        this.isLoadingResults = false;
        this.constructForm();
      }, (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      });
  }

  public filterCountries(countries: Array<string>, name: string): Array<string> {
    return countries.filter(country =>
      country.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  public reduceCountries(countries: Array<string>): Observable<Array<string>> {
    return this.applicationForm.get(['delegation', 'country'])
      .valueChanges
      .startWith(null)
      .map(country => country ? this.filterCountries(countries, country) : countries.slice());
  }

  public isValid(): boolean {
    return this.applicationForm.valid;
  }
}
