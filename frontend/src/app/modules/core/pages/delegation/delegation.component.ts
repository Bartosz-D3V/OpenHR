import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/map';

import { MomentInput } from 'moment';

import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { RegularExpressions } from '../../../../shared/constants/regexps/regular-expressions';
import { Subject } from '../../../../shared/domain/subject/subject';
import { DelegationApplication } from './domain/delegation-application';
import { ISubscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-delegation',
  templateUrl: './delegation.component.html',
  styleUrls: ['./delegation.component.scss'],
  providers: [SubjectDetailsService],
})
export class DelegationComponent implements OnInit, OnDestroy {

  public applicationForm: FormGroup;
  public countryCtrl: FormControl;
  public delegationApplication: DelegationApplication;
  public filteredCountries: Observable<Array<string>>;
  public readonly countries: Array<string>;
  $currentSubject: ISubscription;
  subject: Subject;

  constructor(private _fb: FormBuilder,
              private _subjectDetails: SubjectDetailsService) {
  }

  ngOnInit() {
    this.delegationApplication = new DelegationApplication();
    this.constructForm();
    this.getCurrentSubject();
  }

  ngOnDestroy(): void {
    this.$currentSubject.unsubscribe();
  }

  public constructForm(): void {
    this.countryCtrl = new FormControl();
    this.applicationForm = this._fb.group({
      name: this._fb.group({
        subjectId: [this.delegationApplication.subjectId, [
          Validators.required,
          Validators.pattern(RegularExpressions.NUMBERS_ONLY),
        ]],
        first: ['', Validators.required],
        middle: [''],
        last: ['', Validators.required],
      }),
      organisation: this._fb.group({
        position: ['', Validators.required],
        department: [''],
      }),
      delegation: this._fb.group({
        city: [''],
        objective: ['', Validators.required],
        budget: ['0', [
          Validators.required,
          Validators.min(0),
        ]],
      }),
    });

    this.filteredCountries = this.reduceCountries(this.countries);
    this.applicationForm.get('name').disable();
    this.applicationForm.get('organisation').disable();
  }

  public filterCountries(countries: Array<string>, name: string): Array<string> {
    return countries.filter(country =>
      country.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  public reduceCountries(countries: Array<string>): Observable<Array<string>> {
    return this.countryCtrl
      .valueChanges
      .startWith(null)
      .map(country => country ? this.filterCountries(countries, country) : countries.slice());
  }

  public clearForm(): void {
    this.applicationForm.get('delegation').reset();
    this.countryCtrl.reset();
  }

  public isValid(): boolean {
    return this.applicationForm.valid;
  }

  public setStartDate(startDate: MomentInput): void {
    this.delegationApplication.startDate = startDate;
  }

  public setEndDate(endDate: MomentInput): void {
    this.delegationApplication.endDate = endDate;
  }

  getCurrentSubject(): void {
    this.$currentSubject = this._subjectDetails
      .getCurrentSubject()
      .subscribe((response: Subject) => {
        this.subject = response;
      });
  }

}
