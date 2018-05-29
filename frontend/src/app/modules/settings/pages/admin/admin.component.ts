import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ISubscription } from 'rxjs/Subscription';

import { AdminService } from '@modules/settings/pages/admin/service/admin.service';
import { AllowanceSettings } from '@modules/settings/pages/admin/domain/allowance-settings';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { NotificationService } from '@shared/services/notification/notification.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  providers: [AdminService],
})
export class AdminComponent implements OnInit, OnDestroy {
  private $allowanceSettings: ISubscription;
  public allowanceSettings: AllowanceSettings;

  constructor(
    private _allowanceSettings: AdminService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService
  ) {}

  public ngOnInit(): void {
    this.fetchAllowanceSettings();
  }

  public ngOnDestroy(): void {
    if (this.$allowanceSettings) {
      this.$allowanceSettings.unsubscribe();
    }
  }

  public fetchAllowanceSettings(): void {
    this.$allowanceSettings = this._allowanceSettings.getAdminAllowanceSettings().subscribe((res: AllowanceSettings) => {
      this.allowanceSettings = res;
    });
  }

  public updateAllowanceSettings(allowanceSettings: AllowanceSettings): void {
    this.$allowanceSettings = this._allowanceSettings.updateAdminAllowanceSettings(allowanceSettings).subscribe(
      (res: AllowanceSettings) => {
        this.allowanceSettings = res;
        this._notificationService.openSnackBar('Settings updated', 'OK');
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }
}
