import { EventEmitter, Injectable } from '@angular/core';

import { SiteTheme } from '../domain/site-theme';

@Injectable()
export class ThemeStorageService {
  private static readonly THEME_STORAGE_KEY = 'OpenHR_Theme_Storage_Key';

  public onThemeUpdate: EventEmitter<SiteTheme> = new EventEmitter<SiteTheme>();

  public storeTheme(theme: SiteTheme): void {
    window.localStorage.setItem(ThemeStorageService.THEME_STORAGE_KEY, JSON.stringify(theme));
    this.onThemeUpdate.emit(theme);
  }

  public getStoredTheme(): SiteTheme {
    return JSON.parse(window.localStorage.getItem(ThemeStorageService.THEME_STORAGE_KEY) || null);
  }
}
