import { Injectable } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {
  MatAutocompleteModule,
  MatButtonModule, MatCardModule, MatDatepickerModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule,
  MatNativeDateModule, MatOptionModule, MatToolbarModule
} from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { Employee } from '../../../../shared/domain/subject/employee';
import { ManageEmployeesDataService } from './service/manage-employees-data.service';
import { ManageEmployeesDataComponent } from './manage-employees-data.component';

describe('ManageEmployeesDataComponent', () => {
  let component: ManageEmployeesDataComponent;
  let fixture: ComponentFixture<ManageEmployeesDataComponent>;

  @Injectable()
  class FakeManageEmployeesDataService {
    getEmployees(): Observable<any> {
      return Observable.of([]);
    }
  }

  @Injectable()
  class FakeSubjectDetailsService {
    getSubjectById(subjectId: number): Observable<any> {
      return Observable.of(null);
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ManageEmployeesDataComponent,
        CapitalizePipe,
        PageHeaderComponent,
      ],
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
      ],
      providers: [
        JwtHelperService,
        ErrorResolverService,
        {provide: ManageEmployeesDataService, useClass: FakeManageEmployeesDataService},
        {provide: SubjectDetailsService, useClass: FakeSubjectDetailsService},
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageEmployeesDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('autocomplete methods', () => {
    const employee1: Employee = new Employee('Jack', 'Sparrow', null, null, null, null);
    const employee2: Employee = new Employee('Donnie', 'Darko', null, null, null, null);
    const mockEmployees: Array<Employee> = [employee1, employee2];

    it('filterEmployees method should filter an array by last name of the employee', () => {
      let filteredEmployees: Array<Employee> = component.filterEmployees(mockEmployees, 'Sp');

      expect(filteredEmployees.length).toEqual(1);
      expect(filteredEmployees[0]).toEqual(employee1);

      filteredEmployees = component.filterEmployees(mockEmployees, 'Da');

      expect(filteredEmployees.length).toEqual(1);
      expect(filteredEmployees[0]).toEqual(employee2);
    });

    describe('reduceEmployees', () => {
      let result: Array<Employee>;

      it('should not filter results if input is empty', () => {
        component.reduceEmployees(mockEmployees).subscribe((data: Array<Employee>) => {
          result = data;
        });
        component.employeesCtrl.setValue('');

        expect(result).toBeDefined();
        expect(result).toEqual(mockEmployees);
      });

      it('should filter results accordingly to input value', () => {
        component.reduceEmployees(mockEmployees).subscribe((data: Array<Employee>) => {
          result = data;
        });
        component.employeesCtrl.setValue('Dar');

        expect(result).toBeDefined();
        expect(result[0]).toEqual(employee2);
      });
    });
  });
});
