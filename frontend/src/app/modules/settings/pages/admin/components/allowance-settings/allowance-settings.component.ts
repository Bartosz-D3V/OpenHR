import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { AllowanceSettings } from '@modules/settings/pages/admin/domain/allowance-settings';

@Component({
  selector: 'app-allowance-settings',
  templateUrl: './allowance-settings.component.html',
  styleUrls: ['./allowance-settings.component.scss'],
})
export class AllowanceSettingsComponent implements OnInit, OnDestroy {
  public allowanceSettingsForm: FormGroup;

  @Input() public allowanceSettings: AllowanceSettings;

  constructor(private _fb: FormBuilder) {}

  ngOnInit(): void {
    this.buildForm();
  }

  ngOnDestroy(): void {}

  public buildForm(): void {
    this.allowanceSettingsForm = this._fb.group({
      autoResetAllowance: [''],
      resetDate: [''],
      carryOverUnusedLeave: [''],
      numberOfDaysToCarryOver: ['', Validators.pattern(RegularExpressions.NUMBERS_ONLY)],
    });
  }
}
