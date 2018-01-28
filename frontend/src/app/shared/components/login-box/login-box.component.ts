import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-box',
  templateUrl: './login-box.component.html',
  styleUrls: ['./login-box.component.scss'],
})
export class LoginBoxComponent implements OnInit {
  loginBoxForm: FormGroup;

  constructor(private _fb: FormBuilder) {
  }

  ngOnInit() {
    this.createForm();
  }

  public createForm(): void {
    this.loginBoxForm = this._fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

}
