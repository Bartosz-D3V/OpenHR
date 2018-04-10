import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ISubscription } from 'rxjs/Subscription';

import { SettingsService } from './service/settings.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { User } from '@modules/settings/pages/settings/domain/user';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
  providers: [SettingsService, JwtHelperService],
})
export class SettingsComponent implements OnInit, OnDestroy {
  private $settings: ISubscription;
  private darkModeKey = 'darkMode';
  private localStorage: Storage = window.localStorage;
  public isLoadingResults: boolean;
  public darkModeOn: boolean;
  public user: User;

  public static booleanToFlag(bool: boolean): string {
    return bool ? 'Y' : 'N';
  }

  public static flagToBoolean(text: string): boolean {
    return text === 'Y';
  }

  constructor(
    private _settingsService: SettingsService,
    private _jwtHelper: JwtHelperService,
    private _errorResolver: ErrorResolverService
  ) {}

  ngOnInit(): void {
    this.loadThemeSetting();
    this.fetchUser();
  }

  ngOnDestroy(): void {
    if (this.$settings !== undefined) {
      this.$settings.unsubscribe();
    }
  }

  public turnDarkMode(turnedOn: boolean): void {
    this.localStorage.setItem(this.darkModeKey, SettingsComponent.booleanToFlag(turnedOn));
  }

  public updateUser(): void {
    this._settingsService.updateUser(this.user).subscribe();
  }

  public loadThemeSetting(): void {
    this.darkModeOn = SettingsComponent.flagToBoolean(this.localStorage.getItem(this.darkModeKey));
  }

  public fetchUser(): void {
    this.isLoadingResults = true;
    const subjectId: number = this._jwtHelper.getSubjectId();
    this.$settings = this._settingsService.getUserBySubjectId(subjectId).subscribe(
      (response: User) => {
        this.user = response;
        this.isLoadingResults = false;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }
}
