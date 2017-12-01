import { Injectable, NgZone } from '@angular/core';

@Injectable()
export class ResponsiveHelperService {
  private mobileMediaMatcher: MediaQueryList = matchMedia(`(max-width: 480px)`);

  constructor(private _zone: NgZone) {
    this.mobileMediaMatcher.addListener(mql => _zone.run(() => this.mobileMediaMatcher = mql));
  }

  public isMobile(): boolean {
    return this.mobileMediaMatcher.matches;
  }

}
