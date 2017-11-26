import { Injectable } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { SettingsService } from './settings.service';

describe('SettingsService', () => {
  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  let http: HttpTestingController;
  let settingsService: SettingsService;
  let errorResolverService: ErrorResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
      ],
      providers: [
        SettingsService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
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
