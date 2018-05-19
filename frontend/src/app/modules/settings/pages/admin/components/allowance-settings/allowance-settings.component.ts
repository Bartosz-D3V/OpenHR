import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';

import { NAMED_DATE } from '@config/datepicker-format';
import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { AllowanceSettings } from '@modules/settings/pages/admin/domain/allowance-settings';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';

@Component({
  selector: 'app-allowance-settings',
  templateUrl: './allowance-settings.component.html',
  styleUrls: ['./allowance-settings.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: NAMED_DATE },
  ],
})
export class AllowanceSettingsComponent implements OnInit, OnDestroy {
  public allowanceSettingsForm: FormGroup;

  @Input() public allowanceSettings: AllowanceSettings;

  constructor(private _responsiveHelper: ResponsiveHelperService, private _fb: FormBuilder) {}

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

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }
}
