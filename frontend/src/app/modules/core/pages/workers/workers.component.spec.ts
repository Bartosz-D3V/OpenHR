import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule, MatInputModule, MatPaginatorModule, MatProgressSpinnerModule, MatTableModule } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { WorkersComponent } from '@modules/core/pages/workers/workers.component';
import { WorkersService } from '@modules/core/pages/workers/service/workers.service';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { HttpErrorResponse } from '@angular/common/http';

describe('WorkersComponent', () => {
  let component: WorkersComponent;
  let fixture: ComponentFixture<WorkersComponent>;

  @Injectable()
  class FakeEmployeesService {
    public getEmployees(): Observable<Array<string>> {
      return Observable.of([]);
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CapitalizePipe, PageHeaderComponent, WorkersComponent],
      imports: [
        HttpClientTestingModule,
        NoopAnimationsModule,
        MatTableModule,
        MatPaginatorModule,
        MatFormFieldModule,
        MatInputModule,
        MatProgressSpinnerModule,
      ],
      providers: [
        JwtHelperService,
        {
          provide: WorkersService,
          useClass: FakeEmployeesService,
        },
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('fetchWorkers method', () => {
    const mockWorkers: Array<LightweightSubject> = [new LightweightSubject(1, 'Robinson', 'Cruzoe', 'Sailor')];

    it('should fetch the workers and attach to the data table', () => {
      expect(component.isFetching).toBeTruthy();
      spyOn(component['_workersService'], 'getWorkers').and.returnValue(Observable.of(mockWorkers));
      component.fetchWorkers();

      expect(component.workers.length).toEqual(1);
      expect(component.dataSource.data).toEqual(mockWorkers);
      expect(component.isFetching).toBeFalsy();
    });

    it('should call error resolver if an error occurred', () => {
      const mockError: HttpErrorResponse = new HttpErrorResponse({
        error: 'Unauthorized',
        status: 401,
      });
      expect(component.isFetching).toBeTruthy();
      spyOn(component['_errorResolver'], 'handleError');
      spyOn(component['_workersService'], 'getWorkers').and.returnValue(Observable.throw(mockError));
      component.fetchWorkers();

      expect(component['_errorResolver'].handleError).toHaveBeenCalledWith(mockError.error);
    });
  });

  it('applyFilter should lower-case characters and remove white space around string', () => {
    const mockText1 = '  someTEXT ';
    component.applyFilter(mockText1);
    expect(component.dataSource.filter).toEqual('sometext');

    const mockText2 = '1234 Some Text ';
    component.applyFilter(mockText2);
    expect(component.dataSource.filter).toEqual('1234 some text');
  });
});
