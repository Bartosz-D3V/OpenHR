import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { TokenExpirationModalComponent } from '@shared/components/token-expiration-modal/token-expiration-modal.component';

@Injectable()
export class TokenObserverService {
  constructor(private _jwtHelperService: JwtHelperService, public dialog: MatDialog) {}

  public observe(): void {
    const token: string = this._jwtHelperService.getToken();
    this._jwtHelperService.startIATObserver(token).subscribe(() => {
      if (this.dialog.openDialogs.length === 0) {
        this.openDialog();
      }
    });
  }

  private openDialog(): void {
    this.dialog
      .open(TokenExpirationModalComponent, {
        width: '250px',
        height: '175px',
        hasBackdrop: true,
        disableClose: true,
      })
      .afterClosed(() => this.observe());
  }
}
