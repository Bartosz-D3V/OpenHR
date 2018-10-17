import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Observable } from 'rxjs/Observable';
import { MatCardModule, MatPaginatorModule, MatProgressSpinnerModule, MatTableModule } from '@angular/material';

import { DashboardService } from '@modules/core/pages/dashboard/service/dashboard.service';
import { ChartData } from '@modules/core/pages/dashboard/domain/chart-data';
import { MonthSummary } from '@modules/core/pages/dashboard/domain/month-summary';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { NumberIndicatorComponent } from '@shared/components/number-indicator/number-indicator.component';
import { Month } from '@shared/constants/enumeration/month';
import { DashboardComponent } from './dashboard.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  @Injectable()
  class FakeDashboardService {}

  @Injectable()
  class FakeSubjectDetailsService {
    public getCurrentSubject(): any {
      return Observable.of(null);
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardComponent, NumberIndicatorComponent],
      imports: [
        NoopAnimationsModule,
        HttpClientTestingModule,
        FlexLayoutModule,
        MatTableModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatCardModule,
      ],
      providers: [
        JwtHelperService,
        {
          provide: DashboardService,
          useClass: FakeDashboardService,
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
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('splitMonthlySummaries method', () => {
    const mockMonthSummary1: MonthSummary = new MonthSummary(Month.APRIL, 14);
    const mockMonthSummary2: MonthSummary = new MonthSummary(Month.MAY, 1);
    const mockData: Array<MonthSummary> = [mockMonthSummary1, mockMonthSummary2];

    it('should return object that implements ChartData interface', () => {
      const result: Object = DashboardComponent.splitMonthlySummaries(mockData);

      expect(result.hasOwnProperty('labels')).toBeTruthy();
      expect(result.hasOwnProperty('data')).toBeTruthy();
    });

    it('should return return splitted array of data', () => {
      const result: ChartData = DashboardComponent.splitMonthlySummaries(mockData);

      expect(result.labels).toEqual([Month.APRIL, Month.MAY]);
      expect(result.data).toEqual([14, 1]);
    });
  });
});
