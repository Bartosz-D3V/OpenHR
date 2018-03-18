import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { AbstractControl, FormBuilder, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { Observable } from 'rxjs/Observable';
import { ISubscription } from 'rxjs/Subscription';
import { map, startWith } from 'rxjs/operators';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/filter';

import { DelegationService } from '@modules/core/pages/delegation/service/delegation.service';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { DateRangeComponent } from '@shared/components/date-range/date-range.component';
import { DelegationApplication } from '@shared/domain/application/delegation-application';
import { Subject } from '@shared/domain/subject/subject';
import { Country } from '@shared/domain/country/country';

@Component({
  selector: 'app-delegation',
  templateUrl: './delegation.component.html',
  styleUrls: ['./delegation.component.scss'],
  providers: [
    SubjectDetailsService,
    DelegationService,
    NotificationService,
  ],
})
export class DelegationComponent implements OnInit, OnDestroy {
  private $delegation: ISubscription;
  public subject: Subject;
  public isLoadingResults: boolean;
  public applicationForm: FormGroup;
  public filteredCountries: Observable<Array<Country>>;
  public countries: Array<Country>;

  @ViewChild('dateRange')
  public dateRange: DateRangeComponent;

  @ViewChild(FormGroupDirective)
  public formTemplate: FormGroupDirective;

  constructor(private _delegationService: DelegationService,
              private _subjectDetails: SubjectDetailsService,
              private _notificationService: NotificationService,
              private _errorResolver: ErrorResolverService,
              private _fb: FormBuilder) {
  }

  private resetForm(): void {
    this.applicationForm.get('delegation').reset({budget: 0});
    this.formTemplate.resetForm();
    this.dateRange.reset();
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
        country: ['', Validators.required],
        city: ['', Validators.required],
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
        startWith<string | Country>(''),
        map(value => typeof value === 'string' ? value : value ? value.countryName : null),
        map(name => name ? this.filterCountries(countries, name) : countries? countries.slice(): null),
      );
  }

  public displayCountryName(country?: Country): string | undefined {
    return country ? country.countryName : undefined;
  }

  public save(): void {
    const form: AbstractControl = this.applicationForm;
    const application: DelegationApplication = <DelegationApplication> form.get('delegation').value;
    application.startDate = this.dateRange.startDate;
    application.endDate = this.dateRange.endDate;
    this._delegationService
      .createDelegationApplication(application)
      .subscribe((response: DelegationApplication) => {
        const message = `Application with id ${response.applicationId} has been created`;
        this.resetForm();
        this._notificationService.openSnackBar(message, 'OK');
      }, (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      });
  }

  public isValid(): boolean {
    return this.applicationForm.get('delegation').valid &&
      this.dateRange.dateRangeGroup.valid;
  }
}
