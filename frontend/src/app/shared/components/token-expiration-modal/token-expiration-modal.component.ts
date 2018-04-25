import { Component, Inject, OnDestroy, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { TokenExpirationService } from '@shared/components/token-expiration-modal/service/token-expiration.service';
import { ISubscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-token-expiration-modal',
  templateUrl: './token-expiration-modal.component.html',
  styleUrls: ['./token-expiration-modal.component.scss'],
  providers: [TokenExpirationService],
})
export class TokenExpirationModalComponent implements OnDestroy {
  private $token: ISubscription;

  constructor(
    private _tokenExpiration: TokenExpirationService,
    private _jwtHelper: JwtHelperService,
    private _router: Router,
    public dialogRef: MatDialogRef<TokenExpirationModalComponent>,
    @Optional()
    @Inject(MAT_DIALOG_DATA)
    public data: any
  ) {}

  ngOnDestroy(): void {
    if (this.$token) {
      this.$token.unsubscribe();
    }
  }

  public refreshToken(): void {
    const currentToken: string = this._jwtHelper.getToken();
    this.$token = this._tokenExpiration.refreshToken(currentToken).subscribe((res: HttpResponse<null>) => {
      const token: string = res.headers.get('Authorization');
      this._jwtHelper.saveToken(token);
      this.dialogRef.close();
    });
  }

  public logout(): void {
    this._jwtHelper.removeToken();
    this.dialogRef.close();
    this._router.navigate(['login']);
  }
}
