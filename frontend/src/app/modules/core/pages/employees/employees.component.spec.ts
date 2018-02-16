import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule, MatInputModule, MatPaginatorModule, MatTableModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { Subject } from '../../../../shared/domain/subject/subject';
import { EmployeeInformation } from '../../../../shared/domain/subject/employee-information';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { EmployeesService } from './service/employees.service';
import { EmployeesComponent } from './employees.component';
import { EmployeeData } from './employee-data';
import { Employee } from './domain/employee';

describe('EmployeesComponent', () => {
  let component: EmployeesComponent;
  let fixture: ComponentFixture<EmployeesComponent>;

  @Injectable()
  class FakeEmployeesService {
    public getEmployees(): Observable<Array<string>> {
      return Observable.of([]);
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
        CapitalizePipe,
        PageHeaderComponent,
        EmployeesComponent,
      ],
      imports: [
        HttpClientTestingModule,
        NoopAnimationsModule,
        MatTableModule,
        MatPaginatorModule,
        MatFormFieldModule,
        MatInputModule,
      ],
      providers: [
        {
          provide: EmployeesService, useClass: FakeEmployeesService,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('applyFilter should lower-case characters and remove white space around string', () => {
    const mockText1 = '  someTEXT ';
    component.applyFilter(mockText1);
    expect(component.dataSource.filter).toEqual('sometext');

    const mockText2 = '1234 Some Text ';
    component.applyFilter(mockText2);
    expect(component.dataSource.filter).toEqual('1234 some text');
  });

  it('simplifyEmployeeArray method should convert array of Employee objects into array of EmployeeData object', () => {
    spyOn(component, 'simplifyEmployeeObject').and.callThrough();
    const mockArray: Array<Employee> = [];
    const mockEmployee1 = new Employee();
    const mockEmployee2 = new Employee();
    mockEmployee1.employeeId = 1;
    mockEmployee2.employeeId = 2;
    mockEmployee1.subject = new Subject('Jack', 'Strong', null, null, new EmployeeInformation(null, 'Spy', null, null, null), null);
    mockEmployee2.subject = new Subject('Mikolaj', 'Kopernik', null, null,
      new EmployeeInformation(null, 'Astronomic', null, null, null), null);
    mockArray.push(mockEmployee1);
    mockArray.push(mockEmployee2);
    const convertedArray: Array<EmployeeData> = component.simplifyEmployeeArray(mockArray);

    expect(component.simplifyEmployeeObject).toHaveBeenCalledTimes(2);
    expect(convertedArray[0]).toBeDefined();
    expect(convertedArray[0].id).toBe(1);
    expect(convertedArray[0].name).toEqual('Jack Strong');
    expect(convertedArray[0].position).toEqual('Spy');
    expect(convertedArray[1]).toBeDefined();
    expect(convertedArray[1].id).toBe(2);
    expect(convertedArray[1].name).toEqual('Mikolaj Kopernik');
    expect(convertedArray[1].position).toEqual('Astronomic');
  });

  it('simplifyEmployeeObject method should create simplified object from Employee object', () => {
    const employee: Employee = new Employee();
    employee.employeeId = 1;
    let result: EmployeeData;
    employee.subject = new Subject('John', 'Xavier', null, null, new EmployeeInformation(null, 'Senior Tester', null, null, null), null);
    result = component.simplifyEmployeeObject(employee);

    expect(result).toBeDefined();
    expect(result.id).toBe(1);
    expect(result.name).toEqual('John Xavier');
    expect(result.position).toEqual('Senior Tester');
  });
});
