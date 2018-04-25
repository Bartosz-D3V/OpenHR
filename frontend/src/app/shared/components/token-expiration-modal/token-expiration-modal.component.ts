import { Component, Inject, Optional } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';

@Component({
  selector: 'app-token-expiration-modal',
  templateUrl: './token-expiration-modal.component.html',
  styleUrls: ['./token-expiration-modal.component.scss'],
})
export class TokenExpirationModalComponent {
  constructor(
    private _jwtHelper: JwtHelperService,
    private _router: Router,
    public dialogRef: MatDialogRef<TokenExpirationModalComponent>,
    @Optional()
    @Inject(MAT_DIALOG_DATA)
    public data: any
  ) {}

  public refreshToken(): void {}

  public logout(): void {
    this._jwtHelper.removeToken();
    this._router.navigate(['login']);
  }
}
