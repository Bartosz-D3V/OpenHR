import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { Observable } from 'rxjs/Observable';

import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { HrInformation } from '@shared/domain/subject/hr-information';
import { NumberIndicatorComponent } from '@shared/components/number-indicator/number-indicator.component';
import { DashboardComponent } from './dashboard.component';
import { SingleChartData } from './domain/single-chart-data';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  @Injectable()
  class FakeSubjectDetailsService {
    public getCurrentSubject(): any {
      return Observable.of(null);
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        DashboardComponent,
        NumberIndicatorComponent,
      ],
      imports: [
        NoopAnimationsModule,
        HttpClientTestingModule,
        FlexLayoutModule,
        NgxChartsModule,
      ],
      providers: [
        JwtHelperService,
        {
          provide: SubjectDetailsService, useClass: FakeSubjectDetailsService,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('prepareAllowanceData method should return array of SingleChartData with leave information', () => {
    const mockHrInformation: HrInformation = new HrInformation(25, 5);
    const result: Array<SingleChartData> = component.prepareAllowanceData(mockHrInformation);

    expect(result).toBeDefined();
    expect(result[0]).toBeDefined();
    expect(result[0]).toEqual({'name': 'Total', 'value': 25});
    expect(result[1]).toBeDefined();
    expect(result[1]).toEqual({'name': 'Used', 'value': 5});
  });
});
