import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/map';

import { RegularExpressions } from '../../shared/constants/regular-expressions';
import { DelegationApplication } from './domain/delegation-application';
import { DestinationsDataSource } from './domain/destinations-data-source';

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
    this.dataSource = new DestinationsDataSource(this.delegationApplication.destination);
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
        city: ['']
      })
    });

    this.filteredCountries = this.reduceCountries();
    this.applicationForm.get('name').disable();
    this.applicationForm.get('organisation').disable();
  }

  public filterCountries(name: string): Array<string> {
    return this.countries.filter(country =>
      country.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  public reduceCountries(): Observable<Array<string>> {
    return this.countryCtrl
      .valueChanges
      .startWith(null)
      .map(state => state ? this.filterCountries(state) : this.countries.slice());
  }

  public isValid(): boolean {
    return this.applicationForm.valid;
  }

}
