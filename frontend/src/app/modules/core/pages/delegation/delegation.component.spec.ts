import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MatAutocompleteModule, MatTableModule, MatToolbarModule } from '@angular/material';

import { CalendarModule } from 'primeng/primeng';

import { DelegationComponent } from './delegation.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';

describe('DelegationComponent', () => {
  let component: DelegationComponent;
  let fixture: ComponentFixture<DelegationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        DelegationComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatTableModule,
        MatAutocompleteModule,
        NoopAnimationsModule,
        CalendarModule,
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DelegationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  describe('autocomplete', () => {
    let filteredCountries;
    const mockCountries: Array<string> = [
      'Algeria',
      'South Korea',
      'Germany',
      'Poland',
      'Portugal',
      'Zanzibar'
    ];

    afterEach(() => {
      filteredCountries.length = 0;
    });

    it('filterCountries method should filter an array by name of the country', () => {
      filteredCountries = component.filterCountries(mockCountries, 'Pol');

      expect(filteredCountries.length).toEqual(1);
      expect(filteredCountries[0]).toEqual('Poland');

      filteredCountries = component.filterCountries(mockCountries, 'Po');

      expect(filteredCountries.length).toEqual(2);
      expect(filteredCountries[0]).toEqual('Poland');
      expect(filteredCountries[1]).toEqual('Portugal');
    });

    describe('reduceCountries method', () => {
      let result: Array<string>;

      it('should not filter results if input is empty', () => {
        component.reduceCountries(mockCountries).subscribe((data: Array<string>) => {
          result = data;
        });
        component.countryCtrl.setValue('');

        expect(result).toBeDefined();
        expect(result).toEqual(mockCountries);
      });

      it('should filter results accordingly to input value', () => {
        component.reduceCountries(mockCountries).subscribe((data: Array<string>) => {
          result = data;
        });
        component.countryCtrl.setValue('Ger');

        expect(result).toBeDefined();
        expect(result[0]).toEqual('Germany');
      });

    });

  });

  it('clearForm should clear all fields within delegation form group', () => {
    const formGroup: AbstractControl = component.applicationForm.get('delegation');
    component.countryCtrl.setValue('Belgium');
    formGroup.get('city').setValue('Hamburg');
    formGroup.get('dateRange').setValue(new Date(2020, 9, 3), new Date(2020, 9, 3));
    formGroup.get('budget').setValue('1000');
    component.clearForm();

    expect(component.countryCtrl.value).toBeNull();
    expect(formGroup.get('city').value).toBeNull();
    expect(formGroup.get('dateRange').value).toBeNull();
    expect(formGroup.get('budget').value).toBeNull();
  });

  describe('isValid', () => {
    it('should return false if form is dirty', () => {
      component.applicationForm.setErrors({'error': 'invalid'});

      expect(component.isValid()).toBeFalsy();
    });

    it('should return true if form does not have any errors', () => {
      component.applicationForm.get('delegation')
        .get('objective')
        .setValue('Example value');

      expect(component.isValid()).toBeTruthy();
    });
  });

});
