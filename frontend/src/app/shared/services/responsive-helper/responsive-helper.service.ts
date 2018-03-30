import { Injectable, NgZone } from '@angular/core';

@Injectable()
export class ResponsiveHelperService {
  private medExtraSmallMediaMatcher: MediaQueryList = matchMedia(`(max-width: 480px)`);
  private maxSmallMediaMatcher: MediaQueryList = matchMedia(`(max-width: 840px)`);

  constructor(private _zone: NgZone) {
    this.medExtraSmallMediaMatcher.addListener(mql => _zone.run(() => (this.medExtraSmallMediaMatcher = mql)));
    this.maxSmallMediaMatcher.addListener(mql => _zone.run(() => (this.maxSmallMediaMatcher = mql)));
  }

  public isMobile(): boolean {
    return this.medExtraSmallMediaMatcher.matches;
  }

  public isSmallTablet(): boolean {
    return this.maxSmallMediaMatcher.matches;
  }
}
