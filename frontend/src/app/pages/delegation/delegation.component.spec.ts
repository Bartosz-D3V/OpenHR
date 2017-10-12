import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MdAutocompleteModule, MdTableModule, MdToolbarModule } from '@angular/material';

import { DelegationComponent } from './delegation.component';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';

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
        HttpModule,
        FormsModule,
        ReactiveFormsModule,
        MdToolbarModule,
        MdTableModule,
        MdAutocompleteModule,
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

});
