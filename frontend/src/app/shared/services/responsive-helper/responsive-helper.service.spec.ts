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

  describe('isMobile method', () => {
    it('should return true if screen is less than 480px', inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
      spyOnProperty(service['medExtraSmallMediaMatcher'], 'matches', 'get').and.returnValue(true);

      expect(service.isMobile()).toBeTruthy();
    }));

    it('should return false if screen is greater than 480px', inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
      spyOnProperty(service['medExtraSmallMediaMatcher'], 'matches', 'get').and.returnValue(false);

      expect(service.isMobile()).toBeFalsy();
    }));
  });

  describe('isSmallTablet method', () => {
    it('should return true if screen is less than 840px', inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
      spyOnProperty(service['maxSmallMediaMatcher'], 'matches', 'get').and.returnValue(true);

      expect(service.isSmallTablet()).toBeTruthy();
    }));

    it('should return false if screen is greater than 840px', inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
      spyOnProperty(service['maxSmallMediaMatcher'], 'matches', 'get').and.returnValue(false);

      expect(service.isSmallTablet()).toBeFalsy();
    }));
  });
});
