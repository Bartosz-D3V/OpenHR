import { Injectable } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {
  MatAutocompleteModule,
  MatAutocompleteSelectedEvent,
  MatButtonModule,
  MatCardModule,
  MatDatepickerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatNativeDateModule,
  MatOption,
  MatOptionModule,
  MatProgressSpinnerModule,
  MatSnackBarModule,
  MatToolbarModule,
} from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { _throw } from 'rxjs/observable/throw';

import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Employee } from '@shared/domain/subject/employee';
import { PersonalInformation } from '@shared/domain/subject/personal-information';
import { EmployeeService } from '@shared/services/employee/employee.service';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { ContactInformation } from '@shared/domain/subject/contact-information';
import { Address } from '@shared/domain/subject/address';
import { EmployeeInformation } from '@shared/domain/subject/employee-information';
import { HrInformation } from '@shared/domain/subject/hr-information';
import { Role } from '@shared/domain/subject/role';
import { Manager } from '@shared/domain/subject/manager';
import { NotificationService } from '@shared/services/notification/notification.service';
import { ManagerService } from '@shared/services/manager/manager.service';
import { HrTeamMemberService } from '@shared/services/hr/hr-team-member.service';
import { HrTeamMember } from '@shared/domain/subject/hr-team-member';
import { ManageWorkersDataComponent } from './manage-workers-data.component';

describe('ManageWorkersDataComponent', () => {
  const employee1: Employee = new Employee(
    new PersonalInformation('Jack', 'Sparrow', null, '2000-02-02'),
    new ContactInformation('123456789', 'test@test.com', new Address('First line', 'Second line', 'Third line', 'SA2 92B', 'Gotham', 'US')),
    new EmployeeInformation('KZ 44 09 71 A', 'Junior Software Tester', 'Maintenance Team', '13HJ', '2010-02-02', '2012-02-02'),
    new HrInformation(30, 10),
    Role.EMPLOYEE
  );
  const employee2: Employee = new Employee(
    new PersonalInformation('Donnie', 'Darko', null, '2001-03-03'),
    new ContactInformation(
      '987654321',
      'test2@test.com',
      new Address('First line 1', 'Second line 2', 'Third line 3', 'SB2 92B', 'NYC', 'US')
    ),
    new EmployeeInformation('KZ 44 09 71 B', 'Java Developer', 'Development Team', '13HJ', '2011-02-02', '2013-02-02'),
    new HrInformation(30, 15),
    Role.EMPLOYEE
  );
  const manager1: Manager = new Manager(
    new PersonalInformation('Jim', 'Smith', null, '1999-05-01'),
    new ContactInformation(
      '987654321',
      'test2@test.com',
      new Address('First line 1', 'Second line 2', 'Third line 3', 'SB2 92B', 'NYC', 'US')
    ),
    new EmployeeInformation('KZ 54 09 74 C', 'Java Developer', 'Development Team', '13HJ', '2011-02-02', '2013-02-02'),
    new HrInformation(30, 15),
    Role.MANAGER
  );
  const hrTeamMember1: HrTeamMember = new HrTeamMember(
    new PersonalInformation('Gordon', 'Black', null, '1999-05-01'),
    new ContactInformation(
      '987654321',
      'test2@test.com',
      new Address('First line 1', 'Second line 2', 'Third line 3', 'SB2 92B', 'NYC', 'US')
    ),
    new EmployeeInformation('KZ 54 09 74 C', 'Development Team Manager', 'Development Team', '13HJ', '2011-02-02', '2013-02-02'),
    new HrInformation(30, 15),
    Role.HRTEAMMEMBER
  );
  let fakeEmployeeService: EmployeeService;
  let component: ManageWorkersDataComponent;
  let fixture: ComponentFixture<ManageWorkersDataComponent>;

  @Injectable()
  class FakeEmployeeService {
    updateEmployee(employee: Employee): Observable<Employee> {
      return Observable.of(employee);
    }
  }

  @Injectable()
  class FakeManagerService {
    updateManager(manager: Manager): Observable<Manager> {
      return Observable.of(manager);
    }
  }

  @Injectable()
  class FakeHrTeamMemberService {
    updateHrTeamMember(hrTeamMember: HrTeamMember): Observable<HrTeamMember> {
      return Observable.of(hrTeamMember);
    }
  }

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [ManageWorkersDataComponent, CapitalizePipe, PageHeaderComponent],
        imports: [
          NoopAnimationsModule,
          ReactiveFormsModule,
          FormsModule,
          HttpClientTestingModule,
          MatCardModule,
          MatButtonModule,
          MatFormFieldModule,
          MatInputModule,
          MatToolbarModule,
          MatExpansionModule,
          MatDatepickerModule,
          MatNativeDateModule,
          MatIconModule,
          MatAutocompleteModule,
          MatOptionModule,
          MatSnackBarModule,
          MatProgressSpinnerModule,
        ],
        providers: [
          JwtHelperService,
          NotificationService,
          ErrorResolverService,
          ResponsiveHelperService,
          { provide: EmployeeService, useClass: FakeEmployeeService },
          { provide: ManagerService, useClass: FakeManagerService },
          { provide: HrTeamMemberService, useClass: FakeHrTeamMemberService },
        ],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageWorkersDataComponent);
    fakeEmployeeService = TestBed.get(EmployeeService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('methods for expansion panel', () => {
    it('setStep should set stepNumber', () => {
      component.setStep(2);

      expect(component.stepNumber).toEqual(2);
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

  describe('autocomplete methods', () => {
    const mockEmployees: Array<Employee> = [employee1, employee2];

    it('filterEmployees method should filter an array by last name of the employee', () => {
      let filteredEmployees: Array<Employee> = component.filterSubjects(mockEmployees, 'Sp');

      expect(filteredEmployees.length).toEqual(1);
      expect(filteredEmployees[0]).toEqual(employee1);

      filteredEmployees = component.filterSubjects(mockEmployees, 'Da');

      expect(filteredEmployees.length).toEqual(1);
      expect(filteredEmployees[0]).toEqual(employee2);
    });

    describe('reduceWorkers', () => {
      let result: Array<Employee>;

      it('should not filter results if input is empty', () => {
        component.reduceWorkers(mockEmployees).subscribe((data: Array<Employee>) => {
          result = data;
        });
        component.workersCtrl.setValue('');

        expect(result).toBeDefined();
        expect(result).toEqual(mockEmployees);
      });

      it('should filter results accordingly to input value', () => {
        component.reduceWorkers(mockEmployees).subscribe((data: Array<Employee>) => {
          result = data;
        });
        component.workersCtrl.setValue('Dar');

        expect(result).toBeDefined();
        expect(result[0]).toEqual(employee2);
      });
    });
  });

  describe('displayFullName method', () => {
    it('should return undefined if no parameter was provided', () => {
      expect(component.displayFullName()).toBeUndefined();
    });

    it('should return undefined if subject without personalInformation was provided', () => {
      const emptyEmployee: Employee = new Employee(null, null, null, null, null);

      expect(component.displayFullName(emptyEmployee)).toBeUndefined();
    });

    it('should return full name if subject was provided', () => {
      const fullName: string = component.displayFullName(employee1);

      expect(fullName).toBeDefined();
      expect(typeof fullName).toBe('string');
      expect(fullName).toBe('Jack Sparrow');
    });
  });

  describe('displaySubject method', () => {
    it('should call fetchSelectedEmployee with received selected subject id', () => {
      spyOn(component, 'fetchSelectedEmployee');
      spyOn(component, 'fetchManagers');
      const mockMatOption: MatOption = new MatOption(null, null, null, null);
      employee1.subjectId = 1;
      employee1.role = <any>'EMPLOYEE';
      mockMatOption.value = employee1;
      const $event: MatAutocompleteSelectedEvent = new MatAutocompleteSelectedEvent(null, mockMatOption);
      component.displaySubject($event);

      expect(component.fetchSelectedEmployee).toHaveBeenCalledWith(1);
      expect(component.fetchManagers).toHaveBeenCalled();
    });

    it('should call fetchSelectedManager with received selected subject id', () => {
      spyOn(component, 'fetchSelectedManager');
      spyOn(component, 'fetchHrTeamMembers');
      const mockMatOption: MatOption = new MatOption(null, null, null, null);
      manager1.subjectId = 2;
      manager1.role = <any>'MANAGER';
      mockMatOption.value = manager1;
      const $event: MatAutocompleteSelectedEvent = new MatAutocompleteSelectedEvent(null, mockMatOption);
      component.displaySubject($event);

      expect(component.fetchSelectedManager).toHaveBeenCalledWith(2);
      expect(component.fetchHrTeamMembers).toHaveBeenCalled();
    });

    it('should call fetchSelectedHrTeamMember with received selected subject id', () => {
      spyOn(component, 'fetchSelectedHrTeamMember');
      const mockMatOption: MatOption = new MatOption(null, null, null, null);
      hrTeamMember1.subjectId = 3;
      hrTeamMember1.role = <any>'HRTEAMMEMBER';
      mockMatOption.value = hrTeamMember1;
      const $event: MatAutocompleteSelectedEvent = new MatAutocompleteSelectedEvent(null, mockMatOption);
      component.displaySubject($event);

      expect(component.fetchSelectedHrTeamMember).toHaveBeenCalledWith(3);
    });
  });

  describe('fetchSelectedEmployee method', () => {
    it('should call employeeService with employeeId as an argument', () => {
      const mockEmployeeId: number = employee1.subjectId;
      spyOn(component, 'constructForm').and.callThrough();
      spyOn(component['_employeeService'], 'getEmployee').and.returnValue(Observable.of(employee1));
      component.fetchSelectedEmployee(mockEmployeeId);

      expect(component['_employeeService'].getEmployee).toHaveBeenCalledWith(mockEmployeeId);
      expect(component.constructForm).toHaveBeenCalled();
    });

    it('should call errorResolver in case of an error', () => {
      const mockEmployeeId: number = employee1.subjectId;
      spyOn(component['_errorResolver'], 'handleError');
      spyOn(component['_employeeService'], 'getEmployee').and.returnValue(_throw('Error'));
      component.fetchSelectedEmployee(mockEmployeeId);

      expect(component['_errorResolver'].handleError).toHaveBeenCalled();
    });
  });

  describe('fetchManagers method', () => {
    it('should call managerService and immediately invoke reducer method', () => {
      spyOn(component, 'reduceSupervisors');
      spyOn(component['_managerService'], 'getManagers').and.returnValue(Observable.of([manager1]));
      component.fetchManagers();

      expect(component.managers).toEqual([manager1]);
      expect(component.reduceSupervisors).toHaveBeenCalledWith([manager1]);
    });

    it('should call errorResolver in case of an error', () => {
      spyOn(component['_errorResolver'], 'handleError');
      spyOn(component['_managerService'], 'getManagers').and.returnValue(_throw('Error'));
      component.fetchManagers();

      expect(component['_errorResolver'].handleError).toHaveBeenCalled();
    });
  });

  describe('isMobile', () => {
    it(
      'should return true if screen is less than 480px',
      inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
        component['_responsiveHelper'] = service;
        spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(true);

        expect(component.isMobile()).toBeTruthy();
      })
    );

    it(
      'should return false if screen is greater than 480px',
      inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
        component['_responsiveHelper'] = service;
        spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(false);

        expect(component.isMobile()).toBeFalsy();
      })
    );
  });

  describe('isValid method', () => {
    it('should return true if all form controls are valid', () => {
      component.workerForm = new FormGroup({});
      spyOnProperty(component.workerForm, 'valid', 'get').and.returnValue(true);
      spyOnProperty(component.supervisorCtrl, 'valid', 'get').and.returnValue(true);

      expect(component.isValid()).toBeTruthy();
    });

    it('should return false if workerForm form control is invalid', () => {
      component.workerForm = new FormGroup({});
      spyOnProperty(component.workerForm, 'valid', 'get').and.returnValue(false);
      spyOnProperty(component.supervisorCtrl, 'valid', 'get').and.returnValue(true);

      expect(component.isValid()).toBeFalsy();
    });

    it('should return false if managerFormSpy form control is invalid', () => {
      component.workerForm = new FormGroup({});
      spyOnProperty(component.workerForm, 'valid', 'get').and.returnValue(true);
      spyOnProperty(component.supervisorCtrl, 'valid', 'get').and.returnValue(false);

      expect(component.isValid()).toBeFalsy();
    });

    it('should return false if both form controls are invalid', () => {
      component.workerForm = new FormGroup({});
      spyOnProperty(component.workerForm, 'valid', 'get').and.returnValue(false);
      spyOnProperty(component.supervisorCtrl, 'valid', 'get').and.returnValue(false);

      expect(component.isValid()).toBeFalsy();
    });
  });
});
