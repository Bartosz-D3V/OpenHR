import { Injectable } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ErrorResolverService } from '@shared//services/error-resolver/error-resolver.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { SettingsService } from './settings.service';

describe('SettingsService', () => {
  let http: HttpTestingController;
  let settingsService: SettingsService;
  let errorResolverService: ErrorResolverService;

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {}
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        SettingsService,
        JwtHelperService,
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
    });
    http = TestBed.get(HttpTestingController);
    settingsService = TestBed.get(SettingsService);
    errorResolverService = TestBed.get(ErrorResolverService);
  });

  it('should be created', () => {
    expect(settingsService).toBeTruthy();
  });
});
