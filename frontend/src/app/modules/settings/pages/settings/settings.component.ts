import {Component, OnDestroy, OnInit} from '@angular/core';

import {ISubscription} from 'rxjs/Subscription';

import {SettingsService} from './service/settings.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
  providers: [SettingsService],
})
export class SettingsComponent implements OnInit, OnDestroy {
  public darkModeOn: boolean;
  public notificationsOn: boolean;
  private darkModeKey = 'darkMode';
  private localStorage: Storage = window.localStorage;
  private $notification: ISubscription;

  public static booleanToFlag(bool: boolean): string {
    return bool ? 'Y' : 'N';
  }

  public static flagToBoolean(text: string): boolean {
    return text === 'Y';
  }

  constructor(private _settingsService: SettingsService) {}

  ngOnInit(): void {
    this.loadThemeSetting();
    this.loadNotificationSettings();
  }

  ngOnDestroy(): void {
    this.$notification.unsubscribe();
  }

  public turnDarkMode(turnedOn: boolean): void {
    this.localStorage.setItem(this.darkModeKey, SettingsComponent.booleanToFlag(turnedOn));
  }

  public turnNotification(turnedOn: boolean): void {
    this._settingsService.setNotificationsSetting(123, SettingsComponent.booleanToFlag(turnedOn)).subscribe();
  }

  public loadThemeSetting(): void {
    this.darkModeOn = SettingsComponent.flagToBoolean(this.localStorage.getItem(this.darkModeKey));
  }

  public loadNotificationSettings(): void {
    this.$notification = this._settingsService.getNotificationsSetting(123).subscribe((response: boolean) => {
      this.notificationsOn = response;
    });
  }
}
