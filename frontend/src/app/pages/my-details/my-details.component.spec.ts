import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDetailsComponent } from './my-details.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { MdDatepickerModule, MdExpansionModule, MdNativeDateModule, MdToolbarModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('MyDetailsComponent', () => {
  let component: MyDetailsComponent;
  let fixture: ComponentFixture<MyDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        MyDetailsComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        MdToolbarModule,
        FormsModule,
        ReactiveFormsModule,
        MdToolbarModule,
        MdExpansionModule,
        MdDatepickerModule,
        MdNativeDateModule,
        NoopAnimationsModule,
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  describe('Postcode validator', () => {

    it('should mark form as invalid if input is empty or postcode is invalid', () => {
      const invalidPostcode: String = '11 HG2';

      expect(component.postcodeFormControl.valid).toBeFalsy();
      component.postcodeFormControl.reset();
      component.postcodeFormControl.setValue(invalidPostcode);
      expect(component.postcodeFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is filled and postcode is valid', () => {
      const validPostcode: String = 'SW9 1HZ';
      component.postcodeFormControl.setValue(validPostcode);

      expect(component.postcodeFormControl.valid).toBeTruthy();
    });

  });

  describe('Email validator', () => {

    it('should mark form as invalid if input is empty or email is invalid', () => {
      const invalidEmail: String = 'test@.com';

      expect(component.emailFormControl.valid).toBeFalsy();
      component.emailFormControl.reset();
      component.emailFormControl.setValue(invalidEmail);
      expect(component.emailFormControl.valid).toBeFalsy();
    });

    it('should mark form as valid if input is filled and email is valid', () => {
      const validEmail: String = 'test@test.com';
      component.emailFormControl.setValue(validEmail);

      expect(component.emailFormControl.valid).toBeTruthy();
    });

  });
});
