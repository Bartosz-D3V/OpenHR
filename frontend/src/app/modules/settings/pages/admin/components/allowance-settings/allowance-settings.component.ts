import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
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
export class AllowanceSettingsComponent implements OnInit, OnChanges {
  public allowanceSettingsForm: FormGroup;

  @Input() public allowanceSettings: AllowanceSettings;

  @Output() public onSaveAllowanceSettings: EventEmitter<AllowanceSettings> = new EventEmitter<AllowanceSettings>();

  constructor(private _responsiveHelper: ResponsiveHelperService, private _fb: FormBuilder) {}

  ngOnInit(): void {
    this.buildForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['allowanceSettings']) {
      this.buildForm();
    }
  }

  public buildForm(): void {
    const allowanceSettings: AllowanceSettings = this.allowanceSettings;

    this.allowanceSettingsForm = this._fb.group({
      autoResetAllowance: [allowanceSettings ? allowanceSettings.autoResetAllowance : ''],
      resetDate: [allowanceSettings ? allowanceSettings.resetDate : ''],
      carryOverUnusedLeave: [allowanceSettings ? allowanceSettings.carryOverUnusedLeave : ''],
      numberOfDaysToCarryOver: [
        allowanceSettings ? allowanceSettings.numberOfDaysToCarryOver : 0,
        Validators.pattern(RegularExpressions.NUMBERS_ONLY),
      ],
    });
  }

  public save(): void {
    const allowanceSettings = <AllowanceSettings>this.allowanceSettingsForm.value;
    this.onSaveAllowanceSettings.emit(allowanceSettings);
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

  public isValid(): boolean {
    return this.allowanceSettingsForm.valid;
  }
}
