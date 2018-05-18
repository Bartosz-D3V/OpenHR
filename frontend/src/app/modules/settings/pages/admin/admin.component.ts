import { Component, OnDestroy, OnInit } from '@angular/core';
import { ISubscription } from 'rxjs/Subscription';

import { AdminService } from '@modules/settings/pages/admin/service/admin.service';
import { AllowanceSettings } from '@modules/settings/pages/admin/domain/allowance-settings';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
})
export class AdminComponent implements OnInit, OnDestroy {
  private $allowanceSettings: ISubscription;
  public allowanceSettings: AllowanceSettings;

  constructor(private _allowanceSettings: AdminService) {}

  ngOnInit(): void {
    this.fetchAllowanceSettings();
  }

  ngOnDestroy(): void {}

  public fetchAllowanceSettings(): void {
    this.$allowanceSettings = this._allowanceSettings.getAdminAllowanceSettings().subscribe((res: AllowanceSettings) => {
      this.allowanceSettings = res;
    });
  }
}
