import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatToolbarModule } from '@angular/material';

import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { AccountComponent } from './account.component';

describe('AccountComponent', () => {
  let component: AccountComponent;
  let fixture: ComponentFixture<AccountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AccountComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('passwordsAreIdentical should return true if passwords are the same', () => {
    expect(component.passwordsAreIdentical('password1', 'password1')).toBeTruthy();
    expect(component.accountFormGroup.controls['repeatPasswordController']
      .hasError('passwordDoNotMatch')).toBeFalsy();
  });

  it('passwordsAreIdentical should return false if passwords are not the same and mark the form as dirty', () => {
    expect(component.passwordsAreIdentical('password1', 'password222')).toBeFalsy();
    expect(component.accountFormGroup.controls['repeatPasswordController']
      .hasError('passwordDoNotMatch')).toBeTruthy();
  });
});
