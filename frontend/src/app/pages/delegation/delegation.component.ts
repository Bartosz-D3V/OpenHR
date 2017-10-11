import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { DelegationApplication } from './domain/delegation-application';
import { RegularExpressions } from '../../shared/constants/regular-expressions';

@Component({
  selector: 'app-delegation',
  templateUrl: './delegation.component.html',
  styleUrls: ['./delegation.component.scss'],
})
export class DelegationComponent {

  public applicationForm: FormGroup;
  public delegationApplication: DelegationApplication;

  constructor(private _fb: FormBuilder) {
    this.delegationApplication = new DelegationApplication();
    this.constructForm();
  }

  public constructForm(): void {
    this.applicationForm = this._fb.group({
      name: this._fb.group({
        subjectId: ['',
          Validators.required,
          Validators.pattern(RegularExpressions.NUMBERS_ONLY),
        ],
        first: ['', Validators.required],
        middle: [''],
        last: ['', Validators.required],
      })
    });
  }

  public isValid(): boolean {
    return this.applicationForm.valid;
  }

}
