import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/map';

import { RegularExpressions } from '../../../../shared/constants/regular-expressions';
import { DelegationApplication } from './domain/application/delegation-application';

@Component({
  selector: 'app-delegation',
  templateUrl: './delegation.component.html',
  styleUrls: ['./delegation.component.scss'],
})
export class DelegationComponent implements OnInit {

  public applicationForm: FormGroup;
  public countryCtrl: FormControl;
  public delegationApplication: DelegationApplication;
  public filteredCountries: Observable<Array<string>>;
  public readonly countries: Array<string>;

  constructor(private _fb: FormBuilder) {
  }

  ngOnInit() {
    this.delegationApplication = new DelegationApplication();
    this.constructForm();
  }

  public constructForm(): void {
    this.countryCtrl = new FormControl();
    this.applicationForm = this._fb.group({
      name: this._fb.group({
        subjectId: ['',
          Validators.required,
          Validators.pattern(RegularExpressions.NUMBERS_ONLY),
        ],
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
        budget: ['0', Validators.min(0)],
        dateRange: [''],
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

}
