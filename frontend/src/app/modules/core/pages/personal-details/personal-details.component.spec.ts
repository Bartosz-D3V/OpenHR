import { Injectable } from '@angular/core';
import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatDatepickerModule, MatExpansionModule, MatNativeDateModule, MatToolbarModule } from '@angular/material';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import Spy = jasmine.Spy;

import { PersonalDetailsComponent } from './personal-details.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { StaticModalComponent } from '../../../../shared/components/static-modal/static-modal.component';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { ConfigService } from '../../../../shared/services/config/config.service';

import { SubjectDetailsService } from './service/subject-details.service';
import { SubjectDetails } from './domain/subject-details';
import { PersonalInformation } from './domain/personal-information';
import { EmployeeInformation } from './domain/employee-information';
import { ContactInformation } from './domain/contact-information';
import { Address } from './domain/address';

describe('PersonalDetailsComponent', () => {
  let component: PersonalDetailsComponent;
  let fixture: ComponentFixture<PersonalDetailsComponent>;
  const mockPersonalInformation: PersonalInformation = new PersonalInformation('John', 'Xavier', new Date(), 'Tester');
  const mockAddress: Address = new Address('firstLineAddress', 'secondLineAddress', 'thirdLineAddress', 'postcode', 'city', 'country');
  const mockContactInformation: ContactInformation = new ContactInformation('123456789', 'john.x@company.com', mockAddress);
  const mockEmployeeInformation: EmployeeInformation = new EmployeeInformation('WR 41 45 55 C', '123AS', new Date(), new Date());
  const mockSubject: SubjectDetails = new SubjectDetails(999, mockPersonalInformation, mockContactInformation, mockEmployeeInformation);
  const mockContractTypes: Array<string> = ['Full time', 'Part time'];

  @Injectable()
  class FakeConfigService {
    public getContractTypes(): any {
      return Observable.of(mockContractTypes);
    }
  }

  @Injectable()
  class FakeSubjectDetailsService {
    public getCurrentSubject(): any {
      return Observable.of(mockSubject);
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        PersonalDetailsComponent,
        PageHeaderComponent,
        StaticModalComponent,
        CapitalizePipe,
      ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatToolbarModule,
        MatExpansionModule,
        MatDatepickerModule,
        MatNativeDateModule,
        NoopAnimationsModule,
      ],
      providers: [
        {
          provide: ConfigService, useClass: FakeConfigService,
        },
        {
          provide: SubjectDetailsService, useClass: FakeSubjectDetailsService,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(console, 'log');
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should contains step variable set to 0', () => {
    expect(component.step).toBeDefined();
    expect(component.step).toEqual(0);
  });

  describe('domain object', () => {
    it('should accept middle name as optional parameter', () => {
      mockPersonalInformation.middleName = 'Adam';

      expect(mockPersonalInformation.middleName).toEqual('Adam');
    });

    it('should set middle name to null if not provided', () => {
      mockPersonalInformation.middleName = null;

      expect(mockPersonalInformation.middleName).toBeNull();
    });
  });

  describe('First name validator', () => {

    it('should mark form as valid if input is not empty', () => {
      const name = 'Test';
      component.personalInformationFormGroup.get('firstNameFormControl').setValue(name);

      expect(component.personalInformationFormGroup.get('firstNameFormControl').valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText = '';
      component.personalInformationFormGroup.get('firstNameFormControl').setValue(emptyText);

      expect(component.personalInformationFormGroup.get('firstNameFormControl').valid).toBeFalsy();
    });

  });

  describe('Last name validator', () => {

    it('should mark form as valid if input is not empty', () => {
      const name = 'Test';
      component.personalInformationFormGroup.get('lastNameFormControl').setValue(name);

      expect(component.personalInformationFormGroup.get('lastNameFormControl').valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText = '';
      component.personalInformationFormGroup.get('lastNameFormControl').setValue(emptyText);

      expect(component.personalInformationFormGroup.get('lastNameFormControl').valid).toBeFalsy();
    });

  });

  describe('Date of birth validator', () => {

    it('should mark form as valid if input is not empty', () => {
      const dob: Date = new Date('11 October 1960 15:00 UTC');
      component.personalInformationFormGroup.get('dobFormControl').setValue(dob);

      expect(component.personalInformationFormGroup.get('dobFormControl').valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText = '';
      component.personalInformationFormGroup.get('dobFormControl').setValue(emptyText);

      expect(component.personalInformationFormGroup.get('dobFormControl').valid).toBeFalsy();
    });

  });

  describe('Postcode validator', () => {
    let postcodeFormControl: AbstractControl;

    beforeEach(() => {
      postcodeFormControl = component.contactInformationFormGroup.controls['postcodeFormControl'];
      postcodeFormControl.reset();
    });

    it('should mark form as invalid if input is empty or postcode is invalid', () => {
      const invalidPostcode = '11 HG2';

      expect(postcodeFormControl.valid).toBeFalsy();
      postcodeFormControl.reset();
      postcodeFormControl.setValue(invalidPostcode);
      expect(postcodeFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is filled and postcode is valid', () => {
      const validPostcode = 'SW9 1HZ';
      postcodeFormControl.setValue(validPostcode);

      expect(postcodeFormControl.valid).toBeTruthy();
    });

  });

  describe('Email validator', () => {
    let emailFormControl: AbstractControl;

    beforeEach(() => {
      emailFormControl = component.contactInformationFormGroup.controls['emailFormControl'];
      emailFormControl.reset();
    });

    it('should mark form as invalid if input is empty or email is invalid', () => {
      const invalidEmail = 'test@.com';

      expect(emailFormControl.valid).toBeFalsy();
      emailFormControl.reset();
      emailFormControl.setValue(invalidEmail);
      expect(emailFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is filled and email is valid', () => {
      const validEmail = 'test@test.com';
      emailFormControl.setValue(validEmail);

      expect(emailFormControl.valid).toBeTruthy();
    });

  });

  describe('Telephone validator', () => {
    let telephoneFormControl: AbstractControl;

    beforeEach(() => {
      telephoneFormControl = component.contactInformationFormGroup.controls['telephoneFormControl'];
      telephoneFormControl.reset();
    });

    it('should mark form as invalid if input is empty', () => {
      expect(telephoneFormControl.valid).toBeFalsy();
    });

    it('should mark form as invalid if input is less than 7 digits', () => {
      const invalidTelephone = '123456';
      telephoneFormControl.setValue(invalidTelephone);

      expect(telephoneFormControl.valid).toBeFalsy();
    });

    it('should mark form as invalid if input is greater than 11 digits', () => {
      const invalidTelephone = '123456789101112';
      telephoneFormControl.setValue(invalidTelephone);

      expect(telephoneFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is between 7 and 11 digits', () => {
      const validTelephone1 = '1234567';
      const validTelephone2 = '12345678911';
      const validTelephone3 = '12345678';

      telephoneFormControl.setValue(validTelephone1);
      expect(telephoneFormControl.valid).toBeTruthy();
      telephoneFormControl.reset();

      telephoneFormControl.setValue(validTelephone2);
      expect(telephoneFormControl.valid).toBeTruthy();
      telephoneFormControl.reset();

      telephoneFormControl.setValue(validTelephone3);
      expect(telephoneFormControl.valid).toBeTruthy();
    });

    it('should mark form as invalid if input contains letters', () => {
      const invalidTelephone1 = '1234567abc';

      telephoneFormControl.setValue(invalidTelephone1);
      expect(telephoneFormControl.valid).toBeFalsy();
    });

  });

  describe('Postcode validator', () => {
    let postcodeFormControl: AbstractControl;

    beforeEach(() => {
      postcodeFormControl = component.contactInformationFormGroup.controls['postcodeFormControl'];
      postcodeFormControl.reset();
    });

    it('should mark form as invalid if input is empty', () => {
      expect(postcodeFormControl.valid).toBeFalsy();
    });

    it('should mark form as invalid if postcode is not recognised as UK postcode', () => {
      const invalidPostcode1 = '1WW 098';
      const invalidPostcode2 = 'X 08';

      postcodeFormControl.setValue(invalidPostcode1);
      expect(postcodeFormControl.valid).toBeFalsy();
      postcodeFormControl.reset();

      postcodeFormControl.setValue(invalidPostcode2);
      expect(postcodeFormControl.valid).toBeFalsy();
      postcodeFormControl.reset();
    });

    it('should mark for as valid if postcode is recognised as UK postcode', () => {
      const exampleValidPostcode1 = 'CR2 6XH';
      const exampleValidPostcode2 = 'M1 1AE';

      postcodeFormControl.setValue(exampleValidPostcode1);
      expect(postcodeFormControl.valid).toBeTruthy();
      postcodeFormControl.reset();

      postcodeFormControl.setValue(exampleValidPostcode2);
      expect(postcodeFormControl.valid).toBeTruthy();
      postcodeFormControl.reset();
    });

  });

  describe('NIN validator', () => {
    let ninFormControl: AbstractControl;

    beforeEach(() => {
      ninFormControl = component.employeeDetailsFormGroup.controls['ninFormControl'];
      ninFormControl.reset();
    });

    it('should mark form as invalid if input is empty', () => {
      expect(ninFormControl.valid).toBeFalsy();
    });

    it('should mark form as invalid if NIN is not recognised as UK NIN', () => {
      const invalidNIN1 = 'WR 49 45 55 X';
      const invalidNIN2 = 'W 2 49 35 C';

      ninFormControl.setValue(invalidNIN1);
      expect(ninFormControl.valid).toBeFalsy();
      ninFormControl.reset();

      ninFormControl.setValue(invalidNIN2);
      expect(ninFormControl.valid).toBeFalsy();
      ninFormControl.reset();
    });

    it('should mark for as valid if postcode is recognised as UK NIN', () => {
      const exampleValidNIN1 = 'WR 41 45 55 C';
      const exampleValidNIN2 = 'AX 60 93 31 B';

      ninFormControl.setValue(exampleValidNIN1);
      expect(ninFormControl.valid).toBeTruthy();
      ninFormControl.reset();

      ninFormControl.setValue(exampleValidNIN2);
      expect(ninFormControl.valid).toBeTruthy();
      ninFormControl.reset();
    });

  });

  describe('EmployeeID validator', () => {
    let employeeIdFormControl: AbstractControl;

    beforeEach(() => {
      employeeIdFormControl = component.employeeDetailsFormGroup.controls['employeeIdFormControl'];
      employeeIdFormControl.reset();
    });

    it('should mark form as invalid if input is empty', () => {
      expect(employeeIdFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is not empty', () => {
      const mockEmployeeId = '123AX';
      employeeIdFormControl.setValue(mockEmployeeId);

      expect(employeeIdFormControl.valid).toBeTruthy();
    });

  });

  describe('isValid method', () => {
    let spy1: Spy;
    let spy2: Spy;
    let spy3: Spy;

    const setFormGroupSpies = function (firstGroupFlag: boolean, secondGroupFlag: boolean, thirdGroupFlag: boolean): void {
      spy1.and.returnValue(firstGroupFlag);
      spy2.and.returnValue(secondGroupFlag);
      spy3.and.returnValue(thirdGroupFlag);
    };

    beforeEach(() => {
      spy1 = spyOnProperty(component.personalInformationFormGroup, 'valid', 'get');
      spy2 = spyOnProperty(component.contactInformationFormGroup, 'valid', 'get');
      spy3 = spyOnProperty(component.employeeDetailsFormGroup, 'valid', 'get');
    });

    it('should return true if all formGroups are valid', () => {
      setFormGroupSpies(true, true, true);

      expect(component.isValid()).toBeTruthy();
    });

    it('should return false if at least one formGroup is invalid', () => {
      setFormGroupSpies(false, true, true);
      expect(component.isValid()).toBeFalsy();

      setFormGroupSpies(true, false, true);
      expect(component.isValid()).toBeFalsy();

      setFormGroupSpies(true, true, false);
      expect(component.isValid()).toBeFalsy();

      setFormGroupSpies(false, false, true);
      expect(component.isValid()).toBeFalsy();

      setFormGroupSpies(true, false, false);
      expect(component.isValid()).toBeFalsy();

      setFormGroupSpies(false, true, false);
      expect(component.isValid()).toBeFalsy();

      setFormGroupSpies(false, false, false);
      expect(component.isValid()).toBeFalsy();
    });
  });

  describe('methods for expansion panel', () => {

    it('setStep should set the step', () => {
      component.setStep(1);

      expect(component.step).toEqual(1);
    });

    it('get should return current step', () => {
      component.step = 1;

      expect(component.getStep()).toEqual(1);
    });

    it('next step should increase current step', () => {
      component.step = 0;
      component.nextStep();

      expect(component.step).toEqual(1);
    });

    it('previous step should decrease current step', () => {
      component.step = 1;
      component.prevStep();

      expect(component.step).toEqual(0);
    });

  });

});
