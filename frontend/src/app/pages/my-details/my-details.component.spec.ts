import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDetailsComponent } from './my-details.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { MdDatepickerModule, MdExpansionModule, MdNativeDateModule, MdToolbarModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Subject } from '../../shared/classes/subject/subject';
import { SubjectService } from '../../shared/services/subject/subject.service';
import { Injectable } from '@angular/core';
import { Address } from '../../shared/classes/subject/address';
import { HttpModule } from '@angular/http';

import Spy = jasmine.Spy;

describe('MyDetailsComponent', () => {
  let component: MyDetailsComponent;
  let fixture: ComponentFixture<MyDetailsComponent>;
  const mockSubject = new Subject('John', null, 'Test', new Date(1, 2, 1950),
    'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));

  beforeEach(async(() => {
    @Injectable()
    class FakeSubjectService {
      getCurrentSubject(): Subject {
        return mockSubject;
      }
    }

    TestBed.configureTestingModule({
      declarations: [
        MyDetailsComponent,
        PageHeaderComponent,
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
      providers: [{
        provide: SubjectService, useClass: FakeSubjectService,
      }],
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

  });

  describe('isValid method', () => {

    let spy1: Spy;
    let spy2: Spy;
    let spy3: Spy;

    beforeEach(() => {
      component.postcodeFormControl.reset();
      component.emailFormControl.reset();
      component.telephoneFormControl.reset();

      spy1 = spyOnProperty(component.postcodeFormControl, 'valid', 'get');
      spy2 = spyOnProperty(component.emailFormControl, 'valid', 'get');
      spy3 = spyOnProperty(component.telephoneFormControl, 'valid', 'get');
    });

    it('should return true if all formsControls are valid', () => {
      preparePropertySpies([true, true, true]);

      expect(component.isValid()).toBeTruthy();
    });

    it('should return false if any of the formControl is invalid', () => {
      preparePropertySpies([true, false, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([true, true, false]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([false, true, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([false, false, true]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([true, false, false]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([false, true, false]);
      expect(component.isValid()).toBeFalsy();

      preparePropertySpies([false, false, false]);
      expect(component.isValid()).toBeFalsy();
    });

    const preparePropertySpies = function (logicTable: Array<boolean>): void {
      spy1.and.returnValue(logicTable[0]);
      spy2.and.returnValue(logicTable[1]);
      spy3.and.returnValue(logicTable[2]);
    };

  });
});
