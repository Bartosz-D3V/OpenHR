import { TestBed, inject } from '@angular/core/testing';

import { StyleManagerService } from './style-manager.service';

describe('StyleManagerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        StyleManagerService,
      ],
    });
  });

  afterEach(() => {
    const links = document.head.querySelectorAll('link');
    for (const link of Array.prototype.slice.call(links)) {
      if (link.className.includes('style-manager-')) {
        document.head.removeChild(link);
      }
    }
  });

  it('should be created', inject([StyleManagerService], (service: StyleManagerService) => {
    expect(service).toBeTruthy();
  }));

  it('should add stylesheet to head', inject([StyleManagerService], (sm: StyleManagerService) => {
    sm.setStyle('test', 'test.css');
    const styleEl = document.head.querySelector('.style-manager-test') as HTMLLinkElement;
    expect(styleEl).not.toBeNull();
    expect(styleEl.href.endsWith('test.css')).toBe(true);
  }));

  it('should change existing stylesheet', inject([StyleManagerService], (sm: StyleManagerService) => {
    sm.setStyle('test', 'test.css');
    const styleEl = document.head.querySelector('.style-manager-test') as HTMLLinkElement;
    expect(styleEl).not.toBeNull();
    expect(styleEl.href.endsWith('test.css')).toBe(true);

    sm.setStyle('test', 'new.css');
    expect(styleEl.href.endsWith('new.css')).toBe(true);
  }));

  it('should remove existing stylesheet', inject([StyleManagerService], (sm: StyleManagerService) => {
    sm.setStyle('test', 'test.css');
    let styleEl = document.head.querySelector('.style-manager-test') as HTMLLinkElement;
    expect(styleEl).not.toBeNull();
    expect(styleEl.href.endsWith('test.css')).toBe(true);

    sm.removeStyle('test');
    styleEl = document.head.querySelector('.style-manager-test') as HTMLLinkElement;
    expect(styleEl).toBeNull();
  }));
});
