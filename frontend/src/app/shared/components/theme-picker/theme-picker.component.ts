import { ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation } from '@angular/core';

import { StyleManagerService } from '../../services/style-manager/style-manager.service';
import { ThemeStorageService } from './service/theme-storage.service';
import { SiteThemes } from './domain/site-themes';
import { SiteTheme } from './domain/site-theme';

@Component({
  selector: 'app-theme-picker',
  templateUrl: './theme-picker.component.html',
  styleUrls: ['./theme-picker.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None,
  providers: [ThemeStorageService, StyleManagerService],
})
export class ThemePickerComponent implements OnInit {
  currentTheme: SiteTheme;
  siteThemes: Array<SiteTheme> = SiteThemes.siteThemes;

  constructor(private _themeStorageService: ThemeStorageService, private _styleManagerService: StyleManagerService) {}

  ngOnInit(): void {
    const currentTheme: SiteTheme = this._themeStorageService.getStoredTheme();
    if (currentTheme) {
      this.installTheme(currentTheme);
    }
  }

  installTheme(theme: SiteTheme): void {
    this.currentTheme = this._getCurrentThemeFromHref(theme.href);

    if (theme.isDefault) {
      this._styleManagerService.removeStyle('theme');
    } else {
      this._styleManagerService.setStyle('theme', `assets/${theme.href}`);
    }

    if (this.currentTheme) {
      this._themeStorageService.storeTheme(this.currentTheme);
    }
  }

  private _getCurrentThemeFromHref(href: string): SiteTheme {
    return this.siteThemes.find(theme => theme.href === href);
  }
}
