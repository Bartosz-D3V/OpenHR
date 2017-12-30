import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MatFormFieldModule, MatInputModule, MatPaginatorModule, MatTableModule } from '@angular/material';

import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { EmployeesComponent } from './employees.component';
import { Employee } from './domain/employee';
import { Subject } from '../personal-details/domain/subject';
import { EmployeeInformation } from '../personal-details/domain/employee-information';
import { EmployeeData } from './employee-data';

describe('EmployeesComponent', () => {
  let component: EmployeesComponent;
  let fixture: ComponentFixture<EmployeesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CapitalizePipe,
        PageHeaderComponent,
        EmployeesComponent,
      ],
      imports: [
        NoopAnimationsModule,
        MatTableModule,
        MatPaginatorModule,
        MatFormFieldModule,
        MatInputModule,
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

  it('simplifyEmployeeObject method should create simplified object from Employee object', () => {
    const employee: Employee = new Employee(1);
    let result: EmployeeData;
    employee.subject = new Subject('John', 'Xavier', null, null, new EmployeeInformation(null, 'Senior Tester', null, null, null));
    result = component.simplifyEmployeeObject(employee);

    expect(result).toBeDefined();
    expect(result.id).toBe(1);
    expect(result.name).toEqual('John Xavier');
    expect(result.position).toEqual('Senior Tester');
  });
});
