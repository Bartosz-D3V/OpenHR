import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { Observable } from 'rxjs/Observable';

import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { DashboardComponent } from './dashboard.component';
import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';

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
});
