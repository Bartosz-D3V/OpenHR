import { Injectable } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { MatCardModule, MatSlideToggleModule, MatToolbarModule } from '@angular/material';

import { Observable } from 'rxjs/Observable';

import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { SettingsComponent } from './settings.component';
import { SettingsService } from './service/settings.service';

describe('SettingsComponent', () => {
  let component: SettingsComponent;
  let fixture: ComponentFixture<SettingsComponent>;
  let settingsService: any;

  @Injectable()
  class FakeSettingsService {
    public setNotificationsSetting(subjectId: number, notificationsTurn: string): Observable<boolean> {
      return null;
    }

    public getNotificationsSetting(subjectId: number): Observable<boolean> {
      return Observable.of(false);
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        SettingsComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatSlideToggleModule,
        MatCardModule,
      ],
      providers: [
        {
          provide: SettingsService, useClass: FakeSettingsService,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SettingsComponent);
    component = fixture.componentInstance;
    settingsService = new FakeSettingsService();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load settings on init', () => {
    spyOn(component, 'loadThemeSetting');
    spyOn(component, 'loadNotificationSettings');
    component.ngOnInit();

    expect(component.loadThemeSetting).toHaveBeenCalled();
    expect(component.loadNotificationSettings).toHaveBeenCalled();
  });

  describe('booleanToFlag method', () => {
    it('should  return "Y" if true was passed', () => {
      expect(SettingsComponent.booleanToFlag(true)).toEqual('Y');
    });

    it('should  return "N" if false was passed', () => {
      expect(SettingsComponent.booleanToFlag(false)).toEqual('N');
    });
  });

  describe('flagToBoolean method', () => {
    it('should  return true if "Y" was passed', () => {
      expect(SettingsComponent.flagToBoolean('Y')).toBeTruthy();
    });

    it('should  return false if "N" was passed', () => {
      expect(SettingsComponent.flagToBoolean('N')).toBeFalsy();
    });
  });

  describe('turnDarkMode', () => {
    it('should set localStorage key to "Y" if true was passed', () => {
      component.turnDarkMode(true);

      expect(window.localStorage.getItem('darkMode')).toEqual('Y');
    });

    it('should set localStorage key to "N" if false was passed', () => {
      component.turnDarkMode(false);

      expect(window.localStorage.getItem('darkMode')).toEqual('N');
    });
  });

  describe('loadThemeSetting', () => {
    it('should set darkModeOn field to false if "F" was retained from localStorage', () => {
      window.localStorage.setItem('darkMode', 'F');
      component.loadThemeSetting();

      expect(component['darkModeOn']).toBeFalsy();
    });

    it('should set darkModeOn field to false if nothing was retained from localStorage', () => {
      component.loadThemeSetting();

      expect(component['darkModeOn']).toBeFalsy();
    });

    it('should set darkModeOn field to true if "Y" was retained from localStorage', () => {
      window.localStorage.setItem('darkMode', 'Y');
      component.loadThemeSetting();

      expect(component['darkModeOn']).toBeTruthy();
    });
  });

  xit('turnNotification should invoke service with boolean switch as a flag', () => {
    spyOn(component['_settingsService'], 'setNotificationsSetting');
    component.turnNotification(true);

    expect(component['_settingsService'].setNotificationsSetting).toHaveBeenCalledWith(123, 'Y');
  });
});
