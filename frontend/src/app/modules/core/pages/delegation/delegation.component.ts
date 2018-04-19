import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Params } from '@angular/router';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
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
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { DelegationApplication } from '@shared/domain/application/delegation-application';
import { Subject } from '@shared/domain/subject/subject';
import { Country } from '@shared/domain/country/country';
import { CustomValidators } from '@shared/util/validators/custom-validators';

@Component({
  selector: 'app-delegation',
  templateUrl: './delegation.component.html',
  styleUrls: ['./delegation.component.scss'],
  providers: [SubjectDetailsService, DelegationService, NotificationService, ResponsiveHelperService],
})
export class DelegationComponent implements OnInit, OnDestroy {
  private $delegation: ISubscription;
  public subject: Subject;
  public isLoadingResults: boolean;
  public applicationForm: FormGroup;
  public filteredCountries: Observable<Array<Country>>;
  public countries: Array<Country>;
  public delegationApplication: DelegationApplication;

  constructor(
    private _delegationService: DelegationService,
    private _subjectDetails: SubjectDetailsService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService,
    private _responsiveHelper: ResponsiveHelperService,
    private _fb: FormBuilder,
    private _route: ActivatedRoute
  ) {
    this._route.params.subscribe((param?: Params) => {
      const applicationId: number = param['id'];
      if (applicationId) {
        this.fetchDelegationApplication(applicationId);
      }
    });
  }

  private resetForm(): void {
    this.applicationForm.reset();
    this.applicationForm.markAsUntouched();
    this.applicationForm.markAsPristine();
    this.applicationForm.get('delegation').reset({ budget: 0 });
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
        subjectId: [this.subject.subjectId, [Validators.required, Validators.pattern(RegularExpressions.NUMBERS_ONLY)]],
        first: [this.subject.personalInformation.firstName, Validators.required],
        middle: [this.subject.personalInformation.middleName],
        last: [this.subject.personalInformation.lastName, Validators.required],
      }),
      organisation: this._fb.group({
        position: [this.subject.employeeInformation.position, Validators.required],
        department: [this.subject.employeeInformation.department],
      }),
      delegation: this._fb.group(
        {
          country: [this.delegationApplication ? this.delegationApplication.country : '', Validators.required],
          city: [this.delegationApplication ? this.delegationApplication.city : '', Validators.required],
          objective: [this.delegationApplication ? this.delegationApplication.objective : '', Validators.required],
          budget: [
            this.delegationApplication ? this.delegationApplication.budget : 0,
            Validators.compose([Validators.required, Validators.min(0)]),
          ],
          startDate: [this.delegationApplication ? this.delegationApplication.startDate : '', Validators.compose([Validators.required])],
          endDate: [this.delegationApplication ? this.delegationApplication.endDate : '', Validators.compose([Validators.required])],
        },
        { validator: CustomValidators.validateDateRange }
      ),
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
      (subject: Subject, countries: Array<Country>) => ({ subject, countries })
    ).subscribe(
      pair => {
        this.subject = pair.subject;
        this.countries = pair.countries;
        this.isLoadingResults = false;
        this.constructForm();
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public fetchDelegationApplication(applicationId: number): void {
    this.fetchData();
    this.isLoadingResults = true;
    this.$delegation = this._delegationService.getDelegationApplication(applicationId).subscribe(
      (val: DelegationApplication) => {
        this.delegationApplication = val;
        this.isLoadingResults = false;
        this.constructForm();
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public filterCountries(countries: Array<Country>, name: string): Array<Country> {
    return countries.filter(country => country.countryName.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  public reduceCountries(countries: Array<Country>): Observable<Array<Country>> {
    return this.applicationForm
      .get(['delegation', 'country'])
      .valueChanges.pipe(
        startWith<string | Country>(''),
        map(value => (typeof value === 'string' ? value : value ? value.countryName : null)),
        map(name => (name ? this.filterCountries(countries, name) : countries.slice()))
      );
  }

  public displayCountryName(country?: Country): string | undefined {
    return country ? country.countryName : undefined;
  }

  public save(): void {
    const form: AbstractControl = this.applicationForm;
    const application: DelegationApplication = <DelegationApplication>form.get('delegation').value;
    this.delegationApplication ? this.updateApplication(this.delegationApplication) : this.createApplication(application);
  }

  public createApplication(application: DelegationApplication): void {
    this._delegationService.createDelegationApplication(application).subscribe(
      (response: DelegationApplication) => {
        const message = `Application with id ${response.applicationId} has been created`;
        this.resetForm();
        this._notificationService.openSnackBar(message, 'OK');
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public updateApplication(application: DelegationApplication): void {
    delete application['subject'];
    this._delegationService.updateDelegationApplication(application).subscribe(
      (response: DelegationApplication) => {
        const message = `Application with id ${response.applicationId} has been updated`;
        this.resetForm();
        this._notificationService.openSnackBar(message, 'OK');
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public isValid(): boolean {
    return this.applicationForm.get('delegation').valid;
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }
}
