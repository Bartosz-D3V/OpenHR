import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors } from '@angular/forms';

import { ObjectHelper } from '@shared/util/helpers/object-helper';
import { AsyncValidatorService } from '@shared/util/async-validators/service/async-validator.service';

@Injectable()
export class CustomAsyncValidatorsService {
  constructor(private _asyncValidatorService: AsyncValidatorService) {}

  public validateUsernameIsFree(control: AbstractControl): ValidationErrors {
    return this._asyncValidatorService
      .usernameIsFree(control.value)
      .map(value => (ObjectHelper.stringToBool(value.headers.get('usernameTaken')) ? { usernameTaken: { valid: false } } : null));
  }
}
