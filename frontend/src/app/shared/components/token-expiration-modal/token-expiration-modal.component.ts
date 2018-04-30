import { Component, Inject, OnDestroy, Optional } from '@angular/core';
import { HttpResponse, HttpHeaderResponse, HttpErrorResponse } from '@angular/common/http';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { ISubscription } from 'rxjs/Subscription';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { TokenExpirationService } from '@shared/components/token-expiration-modal/service/token-expiration.service';
import { RefreshToken } from '@shared/components/login-box/domain/refresh-token';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { TokenObserverService } from '@shared/services/token-observer/token-observer.service';

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
    private _errorResolver: ErrorResolverService,
    private _router: Router,
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
    const refreshToken: string = this._jwtHelper.getRefreshToken();
    this.$token = this._tokenExpiration.refreshToken(refreshToken).subscribe(
      (res: HttpResponse<HttpHeaderResponse>) => {
        const token: string = res.headers.get('Authorization');
        this._jwtHelper.saveToken(token);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public logout(): void {
    this._jwtHelper.removeToken();
    this._router.navigate(['login']);
  }
}
