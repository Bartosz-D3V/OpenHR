import { TestBed, inject } from '@angular/core/testing';

import { ResponsiveHelperService } from './responsive-helper.service';

describe('ResponsiveHelperService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ResponsiveHelperService],
    });
  });

  it('should be created', inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
    expect(service).toBeTruthy();
  }));

  it('isMobile should return true if screen is less than 480px', inject([ResponsiveHelperService],
    (service: ResponsiveHelperService) => {
      spyOnProperty(service['mobileMediaMatcher'], 'matches', 'get').and.returnValue(true);

      expect(service.isMobile()).toBeTruthy();
    }));

  it('isMobile should return false if screen is greater than 480px', inject([ResponsiveHelperService],
    (service: ResponsiveHelperService) => {
      spyOnProperty(service['mobileMediaMatcher'], 'matches', 'get').and.returnValue(false);

      expect(service.isMobile()).toBeFalsy();
    }));
});
