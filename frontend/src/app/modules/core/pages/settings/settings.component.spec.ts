import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatSlideToggleModule, MatToolbarModule } from '@angular/material';

import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { SettingsComponent } from './settings.component';

describe('SettingsComponent', () => {
  let component: SettingsComponent;
  let fixture: ComponentFixture<SettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        SettingsComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatSlideToggleModule,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load settings on init', () => {
    spyOn(component, 'loadThemeSetting');
    component.ngOnInit();

    expect(component.loadThemeSetting).toHaveBeenCalled();
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
});
