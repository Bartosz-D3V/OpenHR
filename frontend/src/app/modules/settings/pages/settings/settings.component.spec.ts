import { Injectable } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatCardModule, MatProgressSpinnerModule, MatSlideToggleModule, MatToolbarModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Observable } from 'rxjs/Observable';

import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { SettingsComponent } from './settings.component';
import { SettingsService } from './service/settings.service';
import { User } from '@modules/settings/pages/settings/domain/user';

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
    public createAlert(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SettingsComponent, PageHeaderComponent, CapitalizePipe],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        FlexLayoutModule,
        MatToolbarModule,
        MatSlideToggleModule,
        MatCardModule,
        MatProgressSpinnerModule,
      ],
      providers: [
        {
          provide: SettingsService,
          useClass: FakeSettingsService,
        },
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
    }).compileComponents();
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

  it('updateUser should call service', () => {
    spyOn(component['_settingsService'], 'updateUser').and.returnValue(Observable.of({}));
    const mockUser: User = new User();
    component.user = mockUser;
    component.updateUser();

    expect(component['_settingsService'].updateUser).toHaveBeenCalledWith(mockUser);
  });
});
