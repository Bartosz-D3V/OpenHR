import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
})
export class AccountComponent {

  public accountFormGroup: FormGroup = new FormGroup({
    currentPasswordController: new FormControl('', [
      Validators.required,
    ]),
    newPasswordController: new FormControl('', [
      Validators.required,
    ]),
    repeatPasswordController: new FormControl('', [
      Validators.required,
    ]),
  });

  public passwordsAreIdentical(password1: string, password2: string): boolean {
    if (password1 !== password2) {
      this.accountFormGroup.setErrors({passwordDoNotMatch: true});
      return false;
    }
    return true;
  }

}
