import { AbstractControl, ValidationErrors } from '@angular/forms';

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
}
