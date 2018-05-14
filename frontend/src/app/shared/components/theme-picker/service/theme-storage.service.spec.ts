import { TestBed, inject } from '@angular/core/testing';

import { ThemeStorageService } from './theme-storage.service';
import { SiteTheme } from '../domain/site-theme';

describe('ThemeStorageService', () => {
  const mockTheme: SiteTheme = {
    href: 'test.css',
    accent: 'black',
    primary: 'white',
    isDark: true,
    isDefault: false,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ThemeStorageService],
    });
  });

  afterEach(() => {
    localStorage.clear();
  });

  it(
    'should be created',
    inject([ThemeStorageService], (service: ThemeStorageService) => {
      expect(service).toBeTruthy();
    })
  );

  it(
    'storeTheme should store theme in localStore and emmit its data',
    inject([ThemeStorageService], (service: ThemeStorageService) => {
      service.storeTheme(mockTheme);
      service.onThemeUpdate.subscribe(val => {
        expect(val).toEqual(mockTheme);
      });

      expect(localStorage.getItem('OpenHR_Theme_Storage_Key')).toEqual(JSON.stringify(mockTheme));
    })
  );

  describe('getStoredTheme', () => {
    it(
      'should return theme from localStorage if it exists',
      inject([ThemeStorageService], (service: ThemeStorageService) => {
        localStorage.setItem('OpenHR_Theme_Storage_Key', JSON.stringify(mockTheme));

        expect(service.getStoredTheme()).toEqual(mockTheme);
      })
    );

    it(
      'should return null if there is no stored theme in localStorage',
      inject([ThemeStorageService], (service: ThemeStorageService) => {
        expect(service.getStoredTheme()).toBeNull();
      })
    );
  });
});
