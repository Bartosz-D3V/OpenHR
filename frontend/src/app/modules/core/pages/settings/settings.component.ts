import { Component, OnInit } from '@angular/core';

import { SettingsService } from './service/settings.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
  providers: [SettingsService],
})
export class SettingsComponent implements OnInit {

  public darkModeOn: boolean;
  public notificationsOn: boolean;
  private darkModeKey = 'darkMode';
  private localStorage: Storage = window.localStorage;

  public static booleanToFlag(bool: boolean): string {
    return bool ? 'Y' : 'N';
  }

  public static flagToBoolean(text: string): boolean {
    return text === 'Y';
  }

  constructor(private _settigsService: SettingsService) {
  }

  ngOnInit(): void {
    this.loadThemeSetting();
  }

  public turnDarkMode(turnedOn: boolean): void {
    this.localStorage.setItem(this.darkModeKey, SettingsComponent.booleanToFlag(turnedOn));
  }

  public loadThemeSetting(): void {
    this.darkModeOn = SettingsComponent.flagToBoolean(this.localStorage.getItem(this.darkModeKey));
  }

}
