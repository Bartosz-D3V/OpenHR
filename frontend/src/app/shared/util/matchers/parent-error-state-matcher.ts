import { FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material';

export class ParentErrorStateMatcher implements ErrorStateMatcher {
  public isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted: boolean = form && form.submitted;
    const controlTouched: boolean = control && (control.dirty || control.touched);
    const controlInvalid: boolean = control && control.invalid;
    const parentInvalid: boolean = control && control.parent && control.parent.invalid && (control.parent.dirty || control.parent.touched);

    return isSubmitted || (controlTouched && (controlInvalid || parentInvalid));
  }
}
