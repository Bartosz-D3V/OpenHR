import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ISubscription } from 'rxjs/Subscription';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { TokenExpirationModalComponent } from '@shared/components/token-expiration-modal/token-expiration-modal.component';

@Injectable()
export class TokenObserverService {
  private $jwtHelper: ISubscription;

  constructor(private _jwtHelperService: JwtHelperService, private _dialog: MatDialog) {}

  public observe(): void {
    const token: string = this._jwtHelperService.getToken();
    this.$jwtHelper = this._jwtHelperService.startIATObserver(token).subscribe(() => {
      if (this._dialog.openDialogs.length === 0) {
        this.openDialog();
        this.$jwtHelper.unsubscribe();
      }
    });
  }

  private openDialog(): void {
    this._dialog
      .open(TokenExpirationModalComponent, {
        width: '250px',
        height: '175px',
        hasBackdrop: true,
        disableClose: true,
        data: { cancelled: false },
      })
      .afterClosed()
      .subscribe((data: boolean) => (!data ? this.observe() : this.$jwtHelper.unsubscribe()));
  }
}
