import { Injectable } from '@angular/core';
import { AbstractControl, FormsModule, ReactiveFormsModule, ValidationErrors } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import {
  MatDatepickerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatProgressSpinnerModule,
  MatSnackBarModule,
  MatToolbarModule,
} from '@angular/material';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { StaticModalComponent } from '@shared/components/static-modal/static-modal.component';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Subject } from '@shared/domain/subject/subject';
import { Address } from '@shared/domain/subject/address';
import { PersonalInformation } from '@shared/domain/subject/personal-information';
import { EmployeeInformation } from '@shared/domain/subject/employee-information';
import { ContactInformation } from '@shared/domain/subject/contact-information';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { HrInformation } from '@shared/domain/subject/hr-information';
import { Employee } from '@shared/domain/subject/employee';
import { Role } from '@shared/domain/subject/role';
import { CustomAsyncValidatorsService } from '@shared/util/async-validators/custom-async-validators.service';
import { PersonalDetailsService } from './service/personal-details.service';
import { PersonalDetailsComponent } from './personal-details.component';

describe('PersonalDetailsComponent', () => {
  let component: PersonalDetailsComponent;
  let fixture: ComponentFixture<PersonalDetailsComponent>;
  const mockPersonalInformation: PersonalInformation = new PersonalInformation('John', null, 'Xavier', new Date());
  const mockAddress: Address = new Address('firstLineAddress', 'secondLineAddress', 'thirdLineAddress', 'postcode', 'city', 'country');
  const mockContactInformation: ContactInformation = new ContactInformation('123456789', 'john.x@company.com', mockAddress);
  const mockEmployeeInformation: EmployeeInformation = new EmployeeInformation(
    'WR 41 45 55 C',
    'Tester',
    'WOR123',
    '2020-02-08',
    '2020-02-08',
    '123AS'
  );
  const mockHrInformation: HrInformation = new HrInformation(25, 5);
  const mockSubject: Subject = new Employee(
    mockPersonalInformation,
    mockContactInformation,
    mockEmployeeInformation,
    mockHrInformation,
    Role.EMPLOYEE
  );

  @Injectable()
  class FakeCustomAsyncValidatorsService {
    public validateUsernameIsFree(username: string): ValidationErrors {
      return null;
    }
    public validateEmailIsFree(excludeEmail?: string): ValidationErrors {
      return null;
    }
  }

  @Injectable()
  class FakeSubjectDetailsService {
    public getCurrentSubject(): Observable<Subject> {
      return Observable.of(mockSubject);
    }
  }

  @Injectable()
  class FakePersonalDetailsService {
    public saveSubject(subject: Subject): Observable<Subject> {
      return Observable.of(mockSubject);
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PersonalDetailsComponent, PageHeaderComponent, StaticModalComponent, CapitalizePipe],
      imports: [
        HttpClientTestingModule,
        NoopAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatExpansionModule,
        MatDatepickerModule,
        MatMomentDateModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatSnackBarModule,
        MatProgressSpinnerModule,
      ],
      providers: [
        JwtHelperService,
        ResponsiveHelperService,
        CustomAsyncValidatorsService,
        {
          provide: CustomAsyncValidatorsService,
          useClass: FakeCustomAsyncValidatorsService,
        },
        {
          provide: PersonalDetailsService,
          useClass: FakePersonalDetailsService,
        },
        {
          provide: SubjectDetailsService,
          useClass: FakeSubjectDetailsService,
        },
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should contains stepNumber variable set to 0', () => {
    expect(component.stepNumber).toBeDefined();
    expect(component.stepNumber).toEqual(0);
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
    let firstNameCtrl: AbstractControl;

    beforeEach(() => {
      component.subject = mockSubject;
      component.buildForm();
      firstNameCtrl = component.personalDetailsFormGroup.get(['personalInformation', 'firstName']);
    });

    it('should mark form as valid if input is not empty', () => {
      const name = 'Test';
      firstNameCtrl.setValue(name);

      expect(firstNameCtrl.valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText = '';
      firstNameCtrl.setValue(emptyText);

      expect(firstNameCtrl.valid).toBeFalsy();
    });
  });

  describe('Last name validator', () => {
    let lastNameCtrl: AbstractControl;

    beforeEach(() => {
      component.subject = mockSubject;
      component.buildForm();
      lastNameCtrl = component.personalDetailsFormGroup.get(['personalInformation', 'lastName']);
    });

    it('should mark form as valid if input is not empty', () => {
      const name = 'Test';
      lastNameCtrl.setValue(name);

      expect(lastNameCtrl.valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText = '';
      lastNameCtrl.setValue(emptyText);

      expect(lastNameCtrl.valid).toBeFalsy();
    });
  });

  describe('Date of birth validator', () => {
    let dobNameCtrl: AbstractControl;

    beforeEach(() => {
      component.subject = mockSubject;
      component.buildForm();
      dobNameCtrl = component.personalDetailsFormGroup.get(['personalInformation', 'dateOfBirth']);
    });

    it('should mark form as valid if input is not empty', () => {
      const dob: Date = new Date('11 October 1960 15:00 UTC');
      dobNameCtrl.setValue(dob);

      expect(dobNameCtrl.valid).toBeTruthy();
    });

    it('should mark form as invalid if input is empty', () => {
      const emptyText = '';
      dobNameCtrl.setValue(emptyText);

      expect(dobNameCtrl.valid).toBeFalsy();
    });
  });

  describe('Postcode validator', () => {
    let postcodeFormControl: AbstractControl;

    beforeEach(() => {
      component.subject = mockSubject;
      component.buildForm();
      postcodeFormControl = component.personalDetailsFormGroup.get(['contactInformation', 'address', 'postcode']);
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
      component.subject = mockSubject;
      component.buildForm();
      emailFormControl = component.personalDetailsFormGroup.get(['contactInformation', 'email']);
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
      spyOn(component['_asyncValidator'], 'validateEmailIsFree').and.returnValue(null);

      const validEmail = 'test@test.com';
      emailFormControl.setValue(validEmail);

      expect(emailFormControl.valid).toBeTruthy();
    });
  });

  describe('Telephone validator', () => {
    let telephoneFormControl: AbstractControl;

    beforeEach(() => {
      component.subject = mockSubject;
      component.buildForm();
      telephoneFormControl = component.personalDetailsFormGroup.get(['contactInformation', 'telephone']);
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
      component.subject = mockSubject;
      component.buildForm();
      postcodeFormControl = component.personalDetailsFormGroup.get(['contactInformation', 'address', 'postcode']);
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
      component.subject = mockSubject;
      component.buildForm();
      ninFormControl = component.personalDetailsFormGroup.get(['employeeInformation', 'nationalInsuranceNumber']);
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

  describe('employeeNumber validator', () => {
    let employeeIdFormControl: AbstractControl;

    beforeEach(() => {
      component.subject = mockSubject;
      component.buildForm();
      employeeIdFormControl = component.personalDetailsFormGroup.get(['employeeInformation', 'employeeNumber']);
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
    beforeEach(() => {
      component.subject = mockSubject;
      component.buildForm();
    });

    it('should return true if the form is valid', () => {
      spyOnProperty(component.personalDetailsFormGroup, 'valid', 'get').and.returnValue(true);

      expect(component.isValid()).toBeTruthy();
    });

    it('should return false if the form is invalid', () => {
      spyOnProperty(component.personalDetailsFormGroup, 'valid', 'get').and.returnValue(false);

      expect(component.isValid()).toBeFalsy();
    });
  });

  describe('methods for expansion panel', () => {
    it('setStep should set the stepNumber', () => {
      component.setStep(1);

      expect(component.stepNumber).toEqual(1);
    });

    it('get should return current stepNumber', () => {
      component.stepNumber = 1;

      expect(component.getStep()).toEqual(1);
    });

    it('next stepNumber should increase current stepNumber', () => {
      component.stepNumber = 0;
      component.nextStep();

      expect(component.stepNumber).toEqual(1);
    });

    it('previous stepNumber should decrease current stepNumber', () => {
      component.stepNumber = 1;
      component.prevStep();

      expect(component.stepNumber).toEqual(0);
    });
  });
});
