import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-my-leave',
  templateUrl: './my-leave.component.html',
  styleUrls: ['./my-leave.component.scss']
})
export class MyLeaveComponent implements OnInit {

  public isLinear = false;
  public dateRangeForm: FormGroup;
  public leaveDetailsForm: FormGroup;

  constructor(private _formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.dateRangeForm = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.leaveDetailsForm = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }

}
