import { Injectable } from '@angular/core';
import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatDatepickerModule, MatExpansionModule, MatNativeDateModule, MatToolbarModule } from '@angular/material';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import Spy = jasmine.Spy;

import { Subject } from '../../../../shared/domain/subject/subject';
import { Address } from '../../../../shared/domain/subject/address';
import { PersonalDetailsComponent } from './personal-details.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { StaticModalComponent } from '../../../../shared/components/static-modal/static-modal.component';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { ConfigService } from '../../../../shared/services/config/config.service';
import { PersonalDetailsService } from './service/personal-details.service';

describe('PersonalDetailsComponent', () => {
  let component: PersonalDetailsComponent;
  let fixture: ComponentFixture<PersonalDetailsComponent>;
  const mockSubject: Subject = new Subject('John', 'Test', new Date(1, 2, 1950),
    'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));
  const mockContractTypes: Array<string> = ['Full time', 'Part time'];

  @Injectable()
  class FakeConfigService {
    public getContractTypes(): any {
      return Observable.of(mockContractTypes);
    }
  }

  @Injectable()
  class FakeSubjectService {
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
          provide: PersonalDetailsService, useClass: FakeSubjectService,
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

  describe('First name validator', () => {

    it('should mark form as valid if input is not empty', () => {
      const name: String = 'Test';
      component.personalInformationFormGroup.get('firstNameFormControl').setValue(name);

      expect(component.personalInformationFormGroup.get('firstNameFormControl').valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText: String = '';
      component.personalInformationFormGroup.get('firstNameFormControl').setValue(emptyText);

      expect(component.personalInformationFormGroup.get('firstNameFormControl').valid).toBeFalsy();
    });

  });

  describe('Last name validator', () => {

    it('should mark form as valid if input is not empty', () => {
      const name: String = 'Test';
      component.personalInformationFormGroup.get('lastNameFormControl').setValue(name);

      expect(component.personalInformationFormGroup.get('lastNameFormControl').valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText: String = '';
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
      const emptyText: String = '';
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
      const invalidPostcode: String = '11 HG2';

      expect(postcodeFormControl.valid).toBeFalsy();
      postcodeFormControl.reset();
      postcodeFormControl.setValue(invalidPostcode);
      expect(postcodeFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is filled and postcode is valid', () => {
      const validPostcode: String = 'SW9 1HZ';
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
      const invalidEmail: String = 'test@.com';

      expect(emailFormControl.valid).toBeFalsy();
      emailFormControl.reset();
      emailFormControl.setValue(invalidEmail);
      expect(emailFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is filled and email is valid', () => {
      const validEmail: String = 'test@test.com';
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
      const invalidTelephone: String = '123456';
      telephoneFormControl.setValue(invalidTelephone);

      expect(telephoneFormControl.valid).toBeFalsy();
    });

    it('should mark form as invalid if input is greater than 11 digits', () => {
      const invalidTelephone: String = '123456789101112';
      telephoneFormControl.setValue(invalidTelephone);

      expect(telephoneFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is between 7 and 11 digits', () => {
      const validTelephone1: String = '1234567';
      const validTelephone2: String = '12345678911';
      const validTelephone3: String = '12345678';

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
      const invalidTelephone1: String = '1234567abc';

      telephoneFormControl.setValue(invalidTelephone1);
      expect(telephoneFormControl.valid).toBeFalsy();
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
