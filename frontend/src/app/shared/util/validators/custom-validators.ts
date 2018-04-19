import { AbstractControl, ValidationErrors } from '@angular/forms';
import * as moment from 'moment';

export class CustomValidators {
  public static validatePasswords(abstractCtrl: AbstractControl): ValidationErrors {
    const passwordCtrl: AbstractControl = abstractCtrl.get('password');
    const repeatPasswordCtrl: AbstractControl = abstractCtrl.get('repeatPassword');
    const error: ValidationErrors = {
      passwordDoNotMatch: {
        valid: false,
      },
    };
    if (passwordCtrl.value !== repeatPasswordCtrl.value) {
      repeatPasswordCtrl.setErrors(error);
      return error;
    }
    return null;
  }

  public static validateDateRange(abstractCtrl: AbstractControl): ValidationErrors {
    const startDateCtrl: AbstractControl = abstractCtrl.get('startDate');
    const endDateCtrl: AbstractControl = abstractCtrl.get('endDate');
    const error: ValidationErrors = {
      dateRangeNotValid: {
        valid: false,
      },
    };
    if (moment(endDateCtrl.value).isBefore(startDateCtrl.value)) {
      startDateCtrl.setErrors({ dateRangeNotValid: true });
      endDateCtrl.setErrors({ dateRangeNotValid: true });
      return error;
    }
    return null;
  }
}
