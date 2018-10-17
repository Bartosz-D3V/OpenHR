import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { AbstractControl, FormsModule, ReactiveFormsModule, ValidationErrors } from '@angular/forms';
import {
  MatCheckboxModule,
  MatDatepickerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatNativeDateModule,
  MatOptionModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSnackBarModule,
  MatToolbarModule,
} from '@angular/material';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Observable } from 'rxjs/Observable';

import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { StaticModalComponent } from '@shared/components/static-modal/static-modal.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { HrTeamMemberService } from '@shared/services/hr/hr-team-member.service';
import { ManagerService } from '@shared/services/manager/manager.service';
import { EmployeeService } from '@shared/services/employee/employee.service';
import { PersonalInformation } from '@shared/domain/subject/personal-information';
import { Employee } from '@shared/domain/subject/employee';
import { HrTeamMember } from '@shared/domain/subject/hr-team-member';
import { Manager } from '@shared/domain/subject/manager';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { CustomAsyncValidatorsService } from '@shared/util/async-validators/custom-async-validators.service';
import { AddEmployeeComponent } from './add-employee.component';

describe('AddEmployeeComponent', () => {
  let component: AddEmployeeComponent;
  let fixture: ComponentFixture<AddEmployeeComponent>;
  let notificationService: NotificationService;
  const mockPersonalInformation: PersonalInformation = new PersonalInformation('John', 'Xavier', null, new Date());

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
  class FakeEmployeeService {
    public createEmployee(employee: Employee): Observable<Employee> {
      return Observable.of(employee);
    }
  }

  @Injectable()
  class FakeManagerService {
    public createManager(manager: Manager): Observable<Manager> {
      return Observable.of(manager);
    }
  }

  @Injectable()
  class FakeHrTeamMemberService {
    public createHrTeamMember(hrTeamMember: HrTeamMember): Observable<HrTeamMember> {
      return Observable.of(hrTeamMember);
    }
  }

  @Injectable()
  class FakeNotificationService {
    public openSnackbar(msg: string, btn: string): void {}
  }

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {}
    public handleError(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddEmployeeComponent, PageHeaderComponent, StaticModalComponent, CapitalizePipe],
      imports: [
        HttpClientTestingModule,
        NoopAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatToolbarModule,
        MatExpansionModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatCheckboxModule,
        MatSnackBarModule,
        MatOptionModule,
        MatSelectModule,
        MatProgressSpinnerModule,
      ],
      providers: [
        JwtHelperService,
        ResponsiveHelperService,
        {
          provide: CustomAsyncValidatorsService,
          useClass: FakeCustomAsyncValidatorsService,
        },
        {
          provide: EmployeeService,
          useClass: FakeEmployeeService,
        },
        {
          provide: ManagerService,
          useClass: FakeManagerService,
        },
        {
          provide: HrTeamMemberService,
          useClass: FakeHrTeamMemberService,
        },
        {
          provide: NotificationService,
          useClass: FakeNotificationService,
        },
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEmployeeComponent);
    component = fixture.componentInstance;
    notificationService = TestBed.get(NotificationService);
    fixture.detectChanges();
  });

  it('should create', () => {
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

  it('build form should create the form', () => {
    component.buildForm();

    expect(component.newSubjectForm).toBeDefined();
  });

  it('resetForm method should clean the form', () => {
    component.newSubjectForm.markAsTouched();
    component.newSubjectForm.markAsDirty();
    component.resetForm();

    expect(component.newSubjectForm.touched).toBeFalsy();
    expect(component.newSubjectForm.dirty).toBeFalsy();
    expect(component.newSubjectForm.pristine).toBeTruthy();
  });

  describe('save method', () => {
    beforeEach(() => {
      spyOn(component, 'createSubject');
      spyOn(component, 'resetForm');
    });

    it('should not do anything if the form is not valid', () => {
      component.newSubjectForm.setErrors({ invalid: true });
      component.save();

      expect(component.createSubject).not.toHaveBeenCalled();
      expect(component.resetForm).not.toHaveBeenCalled();
    });

    it('should submit and clear form if form is valid', () => {
      spyOn(component, 'isValid').and.returnValue(true);
      component.save();

      expect(component.createSubject).toHaveBeenCalled();
      expect(component.resetForm).toHaveBeenCalled();
    });
  });

  describe('Form reactive controller', () => {
    beforeAll(() => {
      component.buildForm();
    });

    describe('First name validator', () => {
      let firstNameFormControl: AbstractControl;

      beforeEach(() => {
        firstNameFormControl = component.newSubjectForm.get(['personalInformation', 'firstName']);
      });

      it('should mark form as valid if input is not empty', () => {
        const name = 'Test';
        firstNameFormControl.setValue(name);

        expect(firstNameFormControl.valid).toBeTruthy();
      });

      it('should mark form as invalid if input is empty', () => {
        firstNameFormControl.setValue('');
        firstNameFormControl.markAsTouched();

        expect(firstNameFormControl.valid).toBeFalsy();
      });
    });

    describe('Last name validator', () => {
      let lastNameFormControl: AbstractControl;

      beforeEach(() => {
        lastNameFormControl = component.newSubjectForm.get(['personalInformation', 'lastName']);
      });

      it('should mark form as valid if input is not empty', () => {
        const name = 'Test';
        lastNameFormControl.setValue(name);

        expect(lastNameFormControl.valid).toBeTruthy();
      });

      it('should mark form as invalid if input is empty', () => {
        lastNameFormControl.setValue('');
        lastNameFormControl.markAsTouched();

        expect(lastNameFormControl.valid).toBeFalsy();
      });
    });

    describe('Date of birth validator', () => {
      let dateOfBirthFormControl: AbstractControl;

      beforeEach(() => {
        dateOfBirthFormControl = component.newSubjectForm.get(['personalInformation', 'dateOfBirth']);
      });

      it('should mark form as valid if input is not empty', () => {
        const dob: Date = new Date('11 October 1960 15:00 UTC');
        dateOfBirthFormControl.setValue(dob);

        expect(dateOfBirthFormControl.valid).toBeTruthy();
      });

      it('should mark form as invalid if input is empty', () => {
        dateOfBirthFormControl.setValue('');
        dateOfBirthFormControl.markAsTouched();

        expect(dateOfBirthFormControl.valid).toBeFalsy();
      });
    });

    describe('Postcode validator', () => {
      let postcodeFormControl: AbstractControl;

      beforeEach(() => {
        postcodeFormControl = component.newSubjectForm.get(['contactInformation', 'address', 'postcode']);
        postcodeFormControl.reset();
      });

      it('should mark form as invalid if input is empty ', () => {
        postcodeFormControl.setValue('');
        postcodeFormControl.markAsTouched();

        expect(postcodeFormControl.valid).toBeFalsy();
      });

      it('should mark form as invalid if input is invalid', () => {
        const invalidPostcode = '11 HG2';

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
        emailFormControl = component.newSubjectForm.get(['contactInformation', 'email']);
        emailFormControl.reset();
      });

      it('should mark form as invalid if input is empty', () => {
        emailFormControl.setValue('');
        emailFormControl.markAsTouched();

        expect(emailFormControl.valid).toBeFalsy();
      });

      it('should mark form as invalid if input is invalid', () => {
        const invalidEmail = 'test@.com';
        emailFormControl.setValue(invalidEmail);

        expect(emailFormControl.valid).toBeFalsy();
      });

      it('should mark form as valid if input is filled and email is valid', () => {
        spyOn(component['_asyncValidator'], 'validateEmailIsFree');

        const validEmail = 'test@test.com';
        emailFormControl.setValue(validEmail);

        expect(emailFormControl.valid).toBeTruthy();
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
        telephoneFormControl = component.newSubjectForm.get(['contactInformation', 'telephone']);
        telephoneFormControl.reset();
      });

      it('should mark form as invalid if input is empty', () => {
        telephoneFormControl.setValue('');
        telephoneFormControl.markAsTouched();

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
        postcodeFormControl = component.newSubjectForm.get(['contactInformation', 'address', 'postcode']);
        postcodeFormControl.reset();
      });

      it('should mark form as invalid if input is empty', () => {
        postcodeFormControl.setValue('');
        postcodeFormControl.markAsTouched();

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
        ninFormControl = component.newSubjectForm.get(['employeeInformation', 'nationalInsuranceNumber']);
      });

      afterEach(() => {
        ninFormControl.reset();
      });

      it('should mark form as invalid if input is empty', () => {
        ninFormControl.markAsTouched();

        expect(ninFormControl.valid).toBeFalsy();
      });

      it('should mark form as invalid if NIN is not recognised as UK NIN', () => {
        const invalidNIN1 = 'WR 49 45 55 X';
        const invalidNIN2 = 'W 2 49 35 C';

        ninFormControl.setValue(invalidNIN1);
        expect(ninFormControl.valid).toBeFalsy();

        ninFormControl.setValue(invalidNIN2);
        expect(ninFormControl.valid).toBeFalsy();
      });

      it('should mark for as valid if postcode is recognised as UK NIN', () => {
        const exampleValidNIN1 = 'WR 41 45 55 C';
        const exampleValidNIN2 = 'AX 60 93 31 B';

        ninFormControl.setValue(exampleValidNIN1);
        expect(ninFormControl.valid).toBeTruthy();

        ninFormControl.setValue(exampleValidNIN2);
        expect(ninFormControl.valid).toBeTruthy();
      });
    });

    describe('Employee number validator', () => {
      let employeeNumberFormControl: AbstractControl;

      beforeEach(() => {
        employeeNumberFormControl = component.newSubjectForm.get(['employeeInformation', 'employeeNumber']);
      });

      it('should mark form as valid if input is not empty', () => {
        const code = '12HX';
        employeeNumberFormControl.setValue(code);

        expect(employeeNumberFormControl.valid).toBeTruthy();
      });

      it('should mark form as invalid if input is empty', () => {
        employeeNumberFormControl.setValue('');
        employeeNumberFormControl.markAsTouched();

        expect(employeeNumberFormControl.valid).toBeFalsy();
      });
    });

    describe('Allowance validator', () => {
      let allowanceFormControl: AbstractControl;

      beforeEach(() => {
        allowanceFormControl = component.newSubjectForm.get(['hrInformation', 'allowance']);
      });

      it('should mark form as valid if input is not empty, numerical and greater than 0', () => {
        const allowance = '30';
        allowanceFormControl.setValue(allowance);

        expect(allowanceFormControl.valid).toBeTruthy();
      });

      it('should mark form as invalid if input is empty', () => {
        allowanceFormControl.setValue('');

        expect(allowanceFormControl.valid).toBeFalsy();
      });

      it('should mark form as invalid if input is not numerical', () => {
        allowanceFormControl.setValue('Test');

        expect(allowanceFormControl.valid).toBeFalsy();
      });

      it('should mark form as invalid if input is less than 0', () => {
        allowanceFormControl.setValue(-23);

        expect(allowanceFormControl.valid).toBeFalsy();
      });
    });

    describe('Used allowance validator', () => {
      let usedAllowanceFormControl: AbstractControl;

      beforeEach(() => {
        usedAllowanceFormControl = component.newSubjectForm.get(['hrInformation', 'usedAllowance']);
      });

      it('should mark form as valid if input is not empty, numerical and greater than 0', () => {
        const allowance = '30';
        usedAllowanceFormControl.setValue(allowance);

        expect(usedAllowanceFormControl.valid).toBeTruthy();
      });

      it('should mark form as invalid if input is empty', () => {
        usedAllowanceFormControl.setValue('');

        expect(usedAllowanceFormControl.valid).toBeFalsy();
      });

      it('should mark form as invalid if input is not numerical', () => {
        usedAllowanceFormControl.setValue('Test');

        expect(usedAllowanceFormControl.valid).toBeFalsy();
      });

      it('should mark form as invalid if input is less than 0', () => {
        usedAllowanceFormControl.setValue(-23);

        expect(usedAllowanceFormControl.valid).toBeFalsy();
      });
    });

    describe('Username validator', () => {
      let usernameFormControl: AbstractControl;

      beforeEach(() => {
        usernameFormControl = component.newSubjectForm.get(['user', 'username']);
      });

      it('should mark form as valid if input is not empty', () => {
        spyOn(component['_asyncValidator'], 'validateUsernameIsFree').and.returnValue(null);

        usernameFormControl.setValue('user');

        expect(usernameFormControl.valid).toBeTruthy();
      });

      it('should mark form as invalid if input is empty', () => {
        spyOn(component['_asyncValidator'], 'validateUsernameIsFree').and.returnValue(null);
        usernameFormControl.setValue('');

        expect(usernameFormControl.valid).toBeFalsy();
      });
    });

    describe('Password validator', () => {
      let passwordFormControl: AbstractControl;

      beforeEach(() => {
        passwordFormControl = component.newSubjectForm.get(['user', 'password']);
      });

      it('should mark form as valid if input is not empty', () => {
        const password = 'password';
        passwordFormControl.setValue(password);

        expect(passwordFormControl.valid).toBeTruthy();
      });

      it('should mark form as invalid if input is empty', () => {
        passwordFormControl.setValue('');

        expect(passwordFormControl.valid).toBeFalsy();
      });
    });
  });

  describe('isValid method', () => {
    it('should return true if form is valid', () => {
      spyOnProperty(component.newSubjectForm, 'valid', 'get').and.returnValue(true);

      expect(component.isValid()).toBeTruthy();
    });

    it('should return false if form is not valid', () => {
      spyOnProperty(component.newSubjectForm, 'valid', 'get').and.returnValue(false);

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

  describe('createSubject method', () => {
    const mockSubject: Employee = new Employee(null, null, null, null, null);
    const mockError: HttpErrorResponse = new HttpErrorResponse({
      error: 'Unauthorized',
      status: 401,
    });

    it('should save subject & open a snackbar', () => {
      spyOn(component, 'getServiceMethod').and.returnValue(Observable.of(mockSubject));
      spyOn(component['_notificationService'], 'openSnackBar');
      component.createSubject(mockSubject);
      const msg = `Person with id ${mockSubject.subjectId} has been created`;

      expect(component['_notificationService'].openSnackBar).toHaveBeenCalledWith(msg, 'OK');
    });

    it('should call errorResolver if there was an error', () => {
      spyOn(component, 'getServiceMethod').and.returnValue(Observable.throw(mockError));
      spyOn(component['_errorResolver'], 'handleError');
      component.createSubject(mockSubject);

      expect(component['_errorResolver'].handleError).toHaveBeenCalledWith(mockError.error);
    });
  });

  describe('isMobile', () => {
    it('should return true if screen is less than 480px', inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
      component['_responsiveHelper'] = service;
      spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(true);

      expect(component.isMobile()).toBeTruthy();
    }));

    it('should return false if screen is greater than 480px', inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
      component['_responsiveHelper'] = service;
      spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(false);

      expect(component.isMobile()).toBeFalsy();
    }));
  });
});
