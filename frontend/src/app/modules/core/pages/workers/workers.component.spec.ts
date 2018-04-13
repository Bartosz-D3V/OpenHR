import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule, MatInputModule, MatPaginatorModule, MatTableModule } from '@angular/material';

import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { WorkerInformation } from '@shared/domain/subject/employee-information';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { WorkersComponent } from './employees.component';
import { WorkerData } from './worker-data';
import { Role } from '@shared/domain/subject/role';
import { PersonalInformation } from '@shared/domain/subject/personal-information';

describe('WorkersComponent', () => {
  let component: WorkersComponent;
  let fixture: ComponentFixture<WorkersComponent>;

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {}
  }

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [CapitalizePipe, PageHeaderComponent, WorkersComponent],
        imports: [HttpClientTestingModule, NoopAnimationsModule, MatTableModule, MatPaginatorModule, MatFormFieldModule, MatInputModule],
        providers: [
          {
            provide: WorkersService,
            useClass: FakeWorkersService,
          },
          {
            provide: ErrorResolverService,
            useClass: FakeErrorResolverService,
          },
        ],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkersComponent);
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

  it('simplifyWorkerArray method should convert array of Worker objects into array of WorkerData object', () => {
    spyOn(component, 'simplifyWorkerObject').and.callThrough();
    const mockArray: Array<Worker> = [];
    const mockWorker1 = new Worker(
      new PersonalInformation('Jack', 'Strong', null, null),
      null,
      new WorkerInformation(null, 'Spy', null, null, null, null),
      null,
      null
    );
    const mockWorker2 = new Worker(
      new PersonalInformation('Mikolaj', 'Kopernik', null, null),
      null,
      new WorkerInformation(null, 'Astronomic', null, null, null, null),
      null,
      null
    );
    mockWorker1.subjectId = 1;
    mockWorker2.subjectId = 2;
    mockArray.push(mockWorker1);
    mockArray.push(mockWorker2);
    const convertedArray: Array<WorkerData> = component.simplifyWorkerArray(mockArray);

    expect(component.simplifyWorkerObject).toHaveBeenCalledTimes(2);
    expect(convertedArray[0]).toBeDefined();
    expect(convertedArray[0].id).toBe(1);
    expect(convertedArray[0].name).toEqual('Jack Strong');
    expect(convertedArray[0].position).toEqual('Spy');
    expect(convertedArray[1]).toBeDefined();
    expect(convertedArray[1].id).toBe(2);
    expect(convertedArray[1].name).toEqual('Mikolaj Kopernik');
    expect(convertedArray[1].position).toEqual('Astronomic');
  });

  it('simplifyWorkerObject method should create simplified object from Worker object', () => {
    let result: WorkerData;
    let employee: Worker;
    employee = new Worker(
      new PersonalInformation('John', 'Xavier', null, null),
      null,
      new WorkerInformation(null, 'Senior Tester', null, null, null, null),
      null,
      Role.EMPLOYEE
    );
    employee.subjectId = 1;
    result = component.simplifyWorkerObject(employee);

    expect(result).toBeDefined();
    expect(result.id).toBe(1);
    expect(result.name).toEqual('John Xavier');
    expect(result.position).toEqual('Senior Tester');
  });
});
