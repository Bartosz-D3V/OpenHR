import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Observable } from 'rxjs/Observable';
import { MatPaginatorModule, MatProgressSpinnerModule, MatTableModule } from '@angular/material';

import { DashboardService } from '@modules/core/pages/dashboard/service/dashboard.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { NumberIndicatorComponent } from '@shared/components/number-indicator/number-indicator.component';
import { DashboardComponent } from './dashboard.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  @Injectable()
  class FakeDashboardService {
  }

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
        MatTableModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
      ],
      providers: [
        JwtHelperService,
        {
          provide: DashboardService, useClass: FakeDashboardService,
        },
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
});
