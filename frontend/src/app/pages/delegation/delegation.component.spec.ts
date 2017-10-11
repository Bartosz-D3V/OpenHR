import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MdToolbarModule } from '@angular/material';

import { DelegationComponent } from './delegation.component';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';

describe('DelegationComponent', () => {
  let component: DelegationComponent;
  let fixture: ComponentFixture<DelegationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        DelegationComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        HttpModule,
        FormsModule,
        ReactiveFormsModule,
        MdToolbarModule,
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DelegationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
