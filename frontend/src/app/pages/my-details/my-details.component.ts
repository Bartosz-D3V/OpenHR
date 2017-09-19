import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-my-details',
  templateUrl: './my-details.component.html',
  styleUrls: ['./my-details.component.scss']
})
export class MyDetailsComponent {

  private EMAIL_REGEX: RegExp = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

  emailFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(this.EMAIL_REGEX)
  ]);

}
