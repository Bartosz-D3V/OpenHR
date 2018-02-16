import { Injectable } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {
  MatButtonModule, MatCardModule, MatDatepickerModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule,
  MatNativeDateModule, MatToolbarModule
} from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { ManageEmployeesDataComponent } from './manage-employees-data.component';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';

describe('ManageEmployeesDataComponent', () => {
  let component: ManageEmployeesDataComponent;
  let fixture: ComponentFixture<ManageEmployeesDataComponent>;

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
      ],
      providers: [
        JwtHelperService,
        ErrorResolverService,
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
});
