import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RegularExpressions } from '../../shared/constants/regular-expressions';

@Component({
  selector: 'app-my-details',
  templateUrl: './my-details.component.html',
  styleUrls: ['./my-details.component.scss']
})
export class MyDetailsComponent {

  emailFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(RegularExpressions.EMAIL),
  ]);

}
