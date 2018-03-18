import { Injectable } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AbstractControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatAutocompleteModule, MatCardModule, MatDatepickerModule, MatFormFieldModule, MatInputModule, MatProgressSpinnerModule,
  MatSnackBarModule, MatTableModule,
  MatToolbarModule,
} from '@angular/material';
import { MomentDateModule } from '@angular/material-moment-adapter';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Observable } from 'rxjs/Observable';

import { DelegationService } from '@modules/core/pages/delegation/service/delegation.service';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { DateRangeComponent } from '@shared/components/date-range/date-range.component';
import { DelegationComponent } from './delegation.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { Employee } from '@shared/domain/subject/employee';
import { ContactInformation } from '@shared/domain/subject/contact-information';
import { EmployeeInformation } from '@shared/domain/subject/employee-information';
import { HrInformation } from '@shared/domain/subject/hr-information';
import { Address } from '@shared/domain/subject/address';
import { Role } from '@shared/domain/subject/role';
import { PersonalInformation } from '@shared/domain/subject/personal-information';
import { Country } from '@shared/domain/country/country';
import { By } from '@angular/platform-browser';

describe('DelegationComponent', () => {
  let component: DelegationComponent;
  let fixture: ComponentFixture<DelegationComponent>;
  const employee1: Employee = new Employee(new PersonalInformation('Jack', 'Sparrow', null, '2000-02-02'),
    new ContactInformation('123456789', 'test@test.com', new Address('First line', 'Second line', 'Third line',
      'SA2 92B', 'Gotham', 'US')), new EmployeeInformation('KZ 44 09 71 A', 'Junior Software Tester', 'Maintenance Team',
      '13HJ', '2010-02-02', '2012-02-02'), new HrInformation(30, 10), Role.EMPLOYEE);

  @Injectable()
  class FakeDelegationService {
    getCountries(): Observable<Array<Country>> {
      return Observable.of([]);
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        DelegationComponent,
        PageHeaderComponent,
        CapitalizePipe,
        DateRangeComponent,
      ],
      imports: [
        HttpClientTestingModule,
        MomentDateModule,
        NoopAnimationsModule,
        FormsModule,
        FlexLayoutModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatDatepickerModule,
        MatFormFieldModule,
        MatCardModule,
        MatInputModule,
        MatTableModule,
        MatAutocompleteModule,
        MatProgressSpinnerModule,
        MatSnackBarModule,
      ],
      providers: [
        JwtHelperService,
        NotificationService,
        ErrorResolverService,
        {provide: DelegationService, useClass: FakeDelegationService},
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DelegationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    component.subject = employee1;
    component.constructForm();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  describe('constructForm', () => {
    it('should instantiate application form', () => {
      expect(component.applicationForm).toBeDefined();
    });

    it('should disable appropriate groups', () => {
      expect(component.applicationForm.get('name').disabled).toBeTruthy();
      expect(component.applicationForm.get('organisation').disabled).toBeTruthy();
    });
  });

  describe('name form group', () => {
    let nameValidator: AbstractControl;

    describe('subjectId controller', () => {
      beforeEach(() => {
        nameValidator = component.applicationForm.get(['name', 'subjectId']);
        nameValidator.enable();
      });

      afterEach(() => {
        nameValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        nameValidator.setValue('');

        expect(nameValidator.valid).toBeFalsy();
      });

      it('should mark input as invalid if it is not numerical', () => {
        nameValidator.setValue('Test Subject ID');

        expect(nameValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty and it is numerical', () => {
        nameValidator.setValue('123');

        expect(nameValidator.valid).toBeTruthy();
      });
    });

    describe('first (name) controller', () => {
      beforeEach(() => {
        nameValidator = component.applicationForm.get(['name', 'first']);
        nameValidator.enable();
      });

      afterEach(() => {
        nameValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        nameValidator.setValue('');

        expect(nameValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty', () => {
        nameValidator.setValue('Xavier');

        expect(nameValidator.valid).toBeTruthy();
      });
    });

    describe('last (name) controller', () => {
      beforeEach(() => {
        nameValidator = component.applicationForm.get(['name', 'last']);
        nameValidator.enable();
      });

      afterEach(() => {
        nameValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        nameValidator.setValue('');

        expect(nameValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty', () => {
        nameValidator.setValue('Blackwell');

        expect(nameValidator.valid).toBeTruthy();
      });
    });

  });

  describe('organisation form group', () => {
    let positionValidator: AbstractControl;

    describe('position controller', () => {
      beforeEach(() => {
        positionValidator = component.applicationForm.get(['organisation', 'position']);
        positionValidator.enable();
      });

      afterEach(() => {
        positionValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        positionValidator.setValue('');

        expect(positionValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty', () => {
        positionValidator.setValue('Senior Automation Tester');

        expect(positionValidator.valid).toBeTruthy();
      });
    });
  });

  describe('delegation form group', () => {
    let delegationValidator: AbstractControl;

    describe('objective controller', () => {
      beforeEach(() => {
        delegationValidator = component.applicationForm.get(['delegation', 'objective']);
      });

      afterEach(() => {
        delegationValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        delegationValidator.setValue('');

        expect(delegationValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty', () => {
        delegationValidator.setValue('Training new team abroad');

        expect(delegationValidator.valid).toBeTruthy();
      });
    });

    describe('budget controller', () => {
      beforeEach(() => {
        delegationValidator = component.applicationForm.get(['delegation', 'budget']);
        delegationValidator.enable();
      });

      afterEach(() => {
        delegationValidator.reset();
      });

      it('should be zero by default', () => {
        expect(delegationValidator.value).toBe(0);
      });

      it('should mark input as invalid if it is empty', () => {
        delegationValidator.setValue('');

        expect(delegationValidator.valid).toBeFalsy();
      });

      it('should mark as invalid if the value is less than zero', () => {
        delegationValidator.setValue('-100');

        expect(delegationValidator.valid).toBeFalsy();
      });

      it('should mark as valid if the value is equal to 0', () => {
        delegationValidator.setValue('0');

        expect(delegationValidator.valid).toBeTruthy();
      });

      it('should mark as valid if the value is greater than 0', () => {
        delegationValidator.setValue('2000');

        expect(delegationValidator.valid).toBeTruthy();
      });
    });
  });

  describe('autocomplete', () => {
    let filteredCountries: Array<Country>;
    const mockCountries: Array<Country> = [
      new Country('Algeria', ''),
      new Country('South Korea', ''),
      new Country('Germany', ''),
      new Country('Poland', ''),
      new Country('Portugal', ''),
      new Country('Zanzibar', ''),
    ];

    afterEach(() => {
      filteredCountries.length = 0;
    });

    it('filterCountries method should filter an array by name of the country', () => {
      filteredCountries = component.filterCountries(mockCountries, 'Pol');

      expect(filteredCountries.length).toEqual(1);
      expect(filteredCountries[0].countryName).toEqual('Poland');

      filteredCountries = component.filterCountries(mockCountries, 'Po');

      expect(filteredCountries.length).toEqual(2);
      expect(filteredCountries[0].countryName).toEqual('Poland');
      expect(filteredCountries[1].countryName).toEqual('Portugal');
    });

    describe('reduceCountries method', () => {
      let result: Array<Country>;
      let countryCtrl: AbstractControl;

      beforeEach(() => {
        countryCtrl = component.applicationForm.get(['delegation', 'country']);
      });

      it('should not filter results if input is empty', () => {
        component.reduceCountries(mockCountries).subscribe((data: Array<Country>) => {
          result = data;
        });
        countryCtrl.setValue('');

        expect(result).toBeDefined();
        expect(result).toEqual(mockCountries);
      });

      it('should filter results accordingly to input value', () => {
        component.reduceCountries(mockCountries).subscribe((data: Array<Country>) => {
          result = data;
        });
        countryCtrl.setValue('Ger');

        expect(result).toBeDefined();
        expect(result[0].countryName).toEqual('Germany');
      });

    });

  });

  describe('isValid', () => {
    beforeAll(() => {
      component.isLoadingResults = false;
      fixture.detectChanges();
    });

    it('should return false if form is dirty', () => {
      spyOnProperty(component.applicationForm, 'valid', 'get').and.returnValue(false);

      expect(component.isValid()).toBeFalsy();
    });

    xit('should return true if form does not have any errors', () => {
      spyOnProperty(component.applicationForm, 'valid', 'get').and.returnValue(true);
      spyOnProperty(component.dateRange.dateRangeGroup, 'valid', 'get').and.returnValue(true);

      expect(component.isValid()).toBeTruthy();
    });
  });

});
