import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs/Observable';
import { ISubscription } from 'rxjs/Subscription';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/filter';

import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { Subject } from '@shared/domain/subject/subject';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { DelegationService } from '@modules/core/pages/delegation/service/delegation.service';
import { Country } from '@shared/domain/country/country';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-delegation',
  templateUrl: './delegation.component.html',
  styleUrls: ['./delegation.component.scss'],
  providers: [
    SubjectDetailsService,
    DelegationService,
  ],
})
export class DelegationComponent implements OnInit, OnDestroy {
  private $delegation: ISubscription;
  public subject: Subject;
  public isLoadingResults: boolean;
  public applicationForm: FormGroup;
  public filteredCountries: Observable<Array<Country>>;
  public countries: Array<Country>;

  constructor(private _delegationService: DelegationService,
              private _subjectDetails: SubjectDetailsService,
              private _errorResolver: ErrorResolverService,
              private _fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.fetchData();
  }

  ngOnDestroy(): void {
    if (this.$delegation !== undefined) {
      this.$delegation.unsubscribe();
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

  public fetchData(): void {
    this.isLoadingResults = true;
    this.$delegation = Observable.zip(
      this._subjectDetails.getCurrentSubject(),
      this._delegationService.getCountries(),
      (subject: Subject, countries: Array<Country>) => ({subject, countries}))
      .subscribe((pair) => {
        this.subject = pair.subject;
        this.countries = pair.countries;
        this.isLoadingResults = false;
        this.constructForm();
      }, (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      });
  }

  public filterCountries(countries: Array<Country>, name: string): Array<Country> {
    return countries.filter(country =>
      country.countryName.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  public reduceCountries(countries: Array<Country>): Observable<Array<Country>> {
    return this.applicationForm.get(['delegation', 'country'])
      .valueChanges
      .pipe(
        startWith(''),
        map(name => name ? this.filterCountries(countries, name) : countries.slice())
      );
  }

  public isValid(): boolean {
    return this.applicationForm.valid;
  }
}
