import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors } from '@angular/forms';

import { ObjectHelper } from '@shared/util/helpers/object-helper';
import { AsyncValidatorService } from '@shared/util/async-validators/service/async-validator.service';

@Injectable()
export class CustomAsyncValidatorsService {
  constructor(private _asyncValidatorService: AsyncValidatorService) {}

  public validateUsernameIsFree(): ValidationErrors {
    return (control: AbstractControl): { [key: string]: any } => {
      return this._asyncValidatorService
        .usernameIsFree(control.value)
        .map(value => (ObjectHelper.stringToBool(value.headers.get('usernameTaken')) ? { usernameTaken: { valid: false } } : null));
    };
  }

  public validateEmailIsFree(excludeEmail?: string): ValidationErrors {
    return (control: AbstractControl): { [key: string]: any } => {
      return this._asyncValidatorService
        .emailIsFree(control.value, excludeEmail)
        .map(value => (ObjectHelper.stringToBool(value.headers.get('emailTaken')) ? { emailTaken: { valid: false } } : null));
    };
  }
}
