import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MatAutocompleteModule, MatDatepickerModule, MatTableModule, MatToolbarModule } from '@angular/material';
import { MomentDateModule } from '@angular/material-moment-adapter';
import { FlexLayoutModule } from '@angular/flex-layout';

import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { DateRangeComponent } from '../../../../shared/components/date-range/date-range.component';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { DelegationComponent } from './delegation.component';

describe('DelegationComponent', () => {
  let component: DelegationComponent;
  let fixture: ComponentFixture<DelegationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        DelegationComponent,
        PageHeaderComponent,
        CapitalizePipe,
        DateRangeComponent,
      ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        FlexLayoutModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatDatepickerModule,
        MatTableModule,
        MomentDateModule,
        MatAutocompleteModule,
        NoopAnimationsModule,
      ],
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

  describe('constructForm', () => {
    beforeEach(() => {
      component.constructForm();
    });

    it('should instantiate application form', () => {
      expect(component.applicationForm).toBeDefined();
      expect(component.countryCtrl).toBeDefined();
    });

    it('should disable appropriate groups', () => {
      expect(component.applicationForm.get('name').disabled).toBeTruthy();
      expect(component.applicationForm.get('organisation').disabled).toBeTruthy();
    });
  });

  describe('name form group', () => {
    let nameValidator: AbstractControl;

    beforeEach(() => {
      component.constructForm();
    });

    describe('subjectId controller', () => {
      beforeEach(() => {
        nameValidator = component.applicationForm.get('name.subjectId');
        nameValidator.enable();
      });

      afterEach(() => {
        nameValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        nameValidator.setValue('');

        expect(nameValidator.valid).toBeFalsy();
      });

      it('should mark input as invalid if it is not numerical', () => {
        nameValidator.setValue('Test Subject ID');

        expect(nameValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty and it is numerical', () => {
        nameValidator.setValue('123');

        expect(nameValidator.valid).toBeTruthy();
      });
    });

    describe('first (name) controller', () => {
      beforeEach(() => {
        nameValidator = component.applicationForm.get('name.first');
        nameValidator.enable();
      });

      afterEach(() => {
        nameValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        nameValidator.setValue('');

        expect(nameValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty', () => {
        nameValidator.setValue('Xavier');

        expect(nameValidator.valid).toBeTruthy();
      });
    });

    describe('last (name) controller', () => {
      beforeEach(() => {
        nameValidator = component.applicationForm.get('name.last');
        nameValidator.enable();
      });

      afterEach(() => {
        nameValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        nameValidator.setValue('');

        expect(nameValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty', () => {
        nameValidator.setValue('Blackwell');

        expect(nameValidator.valid).toBeTruthy();
      });
    });

  });

  describe('organisation form group', () => {
    let positionValidator: AbstractControl;

    beforeEach(() => {
      component.constructForm();
    });

    describe('position controller', () => {
      beforeEach(() => {
        positionValidator = component.applicationForm.get('organisation.position');
        positionValidator.enable();
      });

      afterEach(() => {
        positionValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        positionValidator.setValue('');

        expect(positionValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty', () => {
        positionValidator.setValue('Senior Automation Tester');

        expect(positionValidator.valid).toBeTruthy();
      });
    });
  });

  describe('delegation form group', () => {
    let delegationValidator: AbstractControl;

    beforeEach(() => {
      component.constructForm();
    });

    describe('objective controller', () => {
      beforeEach(() => {
        delegationValidator = component.applicationForm.get('delegation.objective');
      });

      afterEach(() => {
        delegationValidator.reset();
      });

      it('should mark input as invalid if it is empty', () => {
        delegationValidator.setValue('');

        expect(delegationValidator.valid).toBeFalsy();
      });

      it('should mark input as valid if it is not empty', () => {
        delegationValidator.setValue('Training new team abroad');

        expect(delegationValidator.valid).toBeTruthy();
      });
    });

    describe('budget controller', () => {
      beforeEach(() => {
        delegationValidator = component.applicationForm.get('delegation.budget');
        delegationValidator.enable();
      });

      afterEach(() => {
        delegationValidator.reset();
      });

      it('should be zero by default', () => {
        expect(delegationValidator.value).toEqual('0');
      });

      it('should mark input as invalid if it is empty', () => {
        delegationValidator.setValue('');

        expect(delegationValidator.valid).toBeFalsy();
      });

      it('should mark as invalid if the value is less than zero', () => {
        delegationValidator.setValue('-100');

        expect(delegationValidator.valid).toBeFalsy();
      });

      it('should mark as valid if the value is equal to 0', () => {
        delegationValidator.setValue('0');

        expect(delegationValidator.valid).toBeTruthy();
      });

      it('should mark as valid if the value is greater than 0', () => {
        delegationValidator.setValue('2000');

        expect(delegationValidator.valid).toBeTruthy();
      });
    });
  });

  describe('autocomplete', () => {
    let filteredCountries;
    const mockCountries: Array<string> = [
      'Algeria',
      'South Korea',
      'Germany',
      'Poland',
      'Portugal',
      'Zanzibar',
    ];

    afterEach(() => {
      filteredCountries.length = 0;
    });

    it('filterCountries method should filter an array by name of the country', () => {
      filteredCountries = component.filterCountries(mockCountries, 'Pol');

      expect(filteredCountries.length).toEqual(1);
      expect(filteredCountries[0]).toEqual('Poland');

      filteredCountries = component.filterCountries(mockCountries, 'Po');

      expect(filteredCountries.length).toEqual(2);
      expect(filteredCountries[0]).toEqual('Poland');
      expect(filteredCountries[1]).toEqual('Portugal');
    });

    describe('reduceCountries method', () => {
      let result: Array<string>;

      it('should not filter results if input is empty', () => {
        component.reduceCountries(mockCountries).subscribe((data: Array<string>) => {
          result = data;
        });
        component.countryCtrl.setValue('');

        expect(result).toBeDefined();
        expect(result).toEqual(mockCountries);
      });

      it('should filter results accordingly to input value', () => {
        component.reduceCountries(mockCountries).subscribe((data: Array<string>) => {
          result = data;
        });
        component.countryCtrl.setValue('Ger');

        expect(result).toBeDefined();
        expect(result[0]).toEqual('Germany');
      });

    });

  });

  it('clearForm should clear all fields within delegation form group', () => {
    const formGroup: AbstractControl = component.applicationForm.get('delegation');
    component.countryCtrl.setValue('Belgium');
    formGroup.get('city').setValue('Hamburg');
    formGroup.get('budget').setValue('1000');
    component.clearForm();

    expect(component.countryCtrl.value).toBeNull();
    expect(formGroup.get('city').value).toBeNull();
    expect(formGroup.get('budget').value).toBeNull();
  });

  describe('isValid', () => {
    it('should return false if form is dirty', () => {
      component.applicationForm.setErrors({'error': 'invalid'});

      expect(component.isValid()).toBeFalsy();
    });

    it('should return true if form does not have any errors', () => {
      component.applicationForm.get('delegation')
        .get('objective')
        .setValue('Example value');

      expect(component.isValid()).toBeTruthy();
    });
  });

});
