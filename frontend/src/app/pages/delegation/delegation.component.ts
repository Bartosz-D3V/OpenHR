import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/map';

import { RegularExpressions } from '../../shared/constants/regular-expressions';
import { DelegationApplication } from './domain/application/delegation-application';
import { DestinationsDataSource } from './domain/data-source/destinations-data-source';
import { Delegation } from './domain/delegation/delegation';
import { Destination } from './domain/destination/destination';

@Component({
  selector: 'app-delegation',
  templateUrl: './delegation.component.html',
  styleUrls: ['./delegation.component.scss'],
})
export class DelegationComponent implements OnInit {

  public applicationForm: FormGroup;
  public delegationApplication: DelegationApplication;
  public filteredCountries: Observable<Array<string>>;
  public readonly countries: Array<string>;
  public readonly displayedColumns: Array<string> = ['country', 'city'];
  public dataSource: DestinationsDataSource;
  public readonly countryCtrl: FormControl;

  constructor(private _fb: FormBuilder) {
    this.countryCtrl = new FormControl();
  }

  ngOnInit() {
    this.delegationApplication = new DelegationApplication();
    this.dataSource = new DestinationsDataSource(this.countries);
    this.constructForm();
  }

  public constructForm(): void {
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
        department: ['']
      }),
      delegation: this._fb.group({
        city: [''],
        dateRange: [''],
        budget: ['0', Validators.min(0)],
      })
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

  public addNewDelegation(country: string, city: string, dateRange: Array<Date>, budget: number): void {
    const destination: Destination = new Destination(country, city);
    const delegation: Delegation = new Delegation(destination, dateRange, budget);

    this.delegationApplication.delegations.push(delegation);
    this.clearForm();
  }

  public clearForm(): void {
    this.applicationForm.get('delegation').reset();
    this.countryCtrl.reset();
  }

  public isValid(): boolean {
    return this.applicationForm.valid;
  }

}
