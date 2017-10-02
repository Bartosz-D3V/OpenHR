import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { MyLeaveService } from './service/my-leave.service';

@Component({
  selector: 'app-my-leave',
  templateUrl: './my-leave.component.html',
  styleUrls: ['./my-leave.component.scss'],
  providers: [MyLeaveService],
})
export class MyLeaveComponent implements OnInit {

  public isLinear = false;
  public dateRangeForm: FormGroup;
  public leaveDetailsForm: FormGroup;
  public leaveTypes: Array<string>;

  constructor(private _formBuilder: FormBuilder,
              private _myLeaveService: MyLeaveService) {
  }

  ngOnInit() {
    this.dateRangeForm = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.leaveDetailsForm = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
    this.getLeaveTypes();
  }

  public getLeaveTypes(): void {
    this._myLeaveService.getLeaveTypes()
      .subscribe((response: Array<string>) => {
        this.leaveTypes = response;
      });
  }

}
