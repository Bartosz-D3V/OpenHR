import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDetailsComponent } from './my-details.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { MdDatepickerModule, MdExpansionModule, MdNativeDateModule, MdToolbarModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Subject } from './classes/subject';
import { Injectable } from '@angular/core';
import { Address } from './classes/address';

import Spy = jasmine.Spy;
import { MyDetailsService } from './service/my-details.service';
import { StaticModalComponent } from '../../shared/components/static-modal/static-modal.component';
import { BrowserDynamicTestingModule } from '@angular/platform-browser-dynamic/testing';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { HttpModule } from '@angular/http';

describe('MyDetailsComponent', () => {
  let component: MyDetailsComponent;
  let fixture: ComponentFixture<MyDetailsComponent>;
  const mockSubject = new Subject('John', null, 'Test', new Date(1, 2, 1950),
    'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));

  @Injectable()
  class FakeSubjectService {
    public getCurrentSubject(): any {
      return Observable.of(mockSubject);
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        MyDetailsComponent,
        PageHeaderComponent,
        StaticModalComponent,
        CapitalizePipe,
      ],
      imports: [
        MdToolbarModule,
        FormsModule,
        HttpModule,
        ReactiveFormsModule,
        MdToolbarModule,
        MdExpansionModule,
        MdDatepickerModule,
        MdNativeDateModule,
        NoopAnimationsModule,
      ],
      providers: [
        {
          provide: MyDetailsService, useClass: FakeSubjectService,
        },
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
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
      component.firstNameFormControl.setValue(name);

      expect(component.firstNameFormControl.valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText: String = '';
      component.firstNameFormControl.setValue(emptyText);

      expect(component.firstNameFormControl.valid).toBeFalsy();
    });

  });

  describe('Last name validator', () => {

    it('should mark form as valid if input is not empty', () => {
      const name: String = 'Test';
      component.lastNameFormControl.setValue(name);

      expect(component.lastNameFormControl.valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText: String = '';
      component.lastNameFormControl.setValue(emptyText);

      expect(component.lastNameFormControl.valid).toBeFalsy();
    });

  });

  describe('Date of birth validator', () => {

    it('should mark form as valid if input is not empty', () => {
      const dob: Date = new Date('11 October 1960 15:00 UTC');
      component.dobFormControl.setValue(dob);

      expect(component.dobFormControl.valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText: String = '';
      component.dobFormControl.setValue(emptyText);

      expect(component.dobFormControl.valid).toBeFalsy();
    });

  });

  describe('Postcode validator', () => {

    beforeEach(() => {
      component.postcodeFormControl.reset();
    });

    it('should mark form as invalid if input is empty or postcode is invalid', () => {
      const invalidPostcode: String = '11 HG2';

      expect(component.postcodeFormControl.valid).toBeFalsy();
      component.postcodeFormControl.reset();
      component.postcodeFormControl.setValue(invalidPostcode);
      expect(component.postcodeFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is filled and postcode is valid', () => {
      const validPostcode: String = 'SW9 1HZ';
      component.postcodeFormControl.setValue(validPostcode);

      expect(component.postcodeFormControl.valid).toBeTruthy();
    });

  });

  describe('Email validator', () => {

    beforeEach(() => {
      component.emailFormControl.reset();
    });

    it('should mark form as invalid if input is empty or email is invalid', () => {
      const invalidEmail: String = 'test@.com';

      expect(component.emailFormControl.valid).toBeFalsy();
      component.emailFormControl.reset();
      component.emailFormControl.setValue(invalidEmail);
      expect(component.emailFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is filled and email is valid', () => {
      const validEmail: String = 'test@test.com';
      component.emailFormControl.setValue(validEmail);

      expect(component.emailFormControl.valid).toBeTruthy();
    });

  });

  describe('Telephone validator', () => {

    beforeEach(() => {
      component.telephoneFormControl.reset();
    });

    it('should mark form as invalid if input is empty', () => {
      expect(component.telephoneFormControl.valid).toBeFalsy();
    });

    it('should mark form as invalid if input is less than 7 digits', () => {
      const invalidTelephone: String = '123456';
      component.telephoneFormControl.setValue(invalidTelephone);

      expect(component.telephoneFormControl.valid).toBeFalsy();
    });

    it('should mark form as invalid if input is greater than 11 digits', () => {
      const invalidTelephone: String = '123456789101112';
      component.telephoneFormControl.setValue(invalidTelephone);

      expect(component.telephoneFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is between 7 and 11 digits', () => {
      const validTelephone1: String = '1234567';
      const validTelephone2: String = '12345678911';
      const validTelephone3: String = '12345678';

      component.telephoneFormControl.setValue(validTelephone1);
      expect(component.telephoneFormControl.valid).toBeTruthy();
      component.telephoneFormControl.reset();

      component.telephoneFormControl.setValue(validTelephone2);
      expect(component.telephoneFormControl.valid).toBeTruthy();
      component.telephoneFormControl.reset();

      component.telephoneFormControl.setValue(validTelephone3);
      expect(component.telephoneFormControl.valid).toBeTruthy();
    });

    it('should mark form as invalid if input contains letters', () => {
      const invalidTelephone1: String = '1234567abc';

      component.telephoneFormControl.setValue(invalidTelephone1);
      expect(component.telephoneFormControl.valid).toBeFalsy();
    });

  });

  describe('isValid method', () => {

    let spy1: Spy;
    let spy2: Spy;
    let spy3: Spy;
    let spy4: Spy;
    let spy5: Spy;
    let spy6: Spy;

    beforeEach(() => {
      component.postcodeFormControl.reset();
      component.emailFormControl.reset();
      component.telephoneFormControl.reset();

      spy1 = spyOnProperty(component.postcodeFormControl, 'valid', 'get');
      spy2 = spyOnProperty(component.emailFormControl, 'valid', 'get');
      spy3 = spyOnProperty(component.telephoneFormControl, 'valid', 'get');
      spy4 = spyOnProperty(component.firstNameFormControl, 'valid', 'get');
      spy5 = spyOnProperty(component.lastNameFormControl, 'valid', 'get');
      spy6 = spyOnProperty(component.dobFormControl, 'valid', 'get');
    });

    const preparePropertySpies = function (logicTable: Array<boolean>): void {
      spy1.and.returnValue(logicTable[0]);
      spy2.and.returnValue(logicTable[1]);
      spy3.and.returnValue(logicTable[2]);
      spy4.and.returnValue(logicTable[3]);
      spy5.and.returnValue(logicTable[4]);
      spy6.and.returnValue(logicTable[5]);
    };

    it('should return true if all formsControls are valid', () => {
      preparePropertySpies([true, true, true, true, true, true]);

      expect(component.isValid()).toBeTruthy();
    });

    it('should return false if any of the formControl is invalid', () => {
      preparePropertySpies([true, false, true, true, true, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([true, true, false, true, true, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([false, true, true, true, true, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([false, false, true, true, true, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([true, false, false, true, true, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([false, true, false, true, true, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([false, false, false, true, true, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([true, true, true, false, true, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([true, true, true, true, false, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([true, true, true, true, true, false]);
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
