import { TestBed, async, inject } from '@angular/core/testing';

import { MainAppGuard } from './main-app.guard';

describe('MainAppGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MainAppGuard]
    });
  });

  it('should ...', inject([MainAppGuard], (guard: MainAppGuard) => {
    expect(guard).toBeTruthy();
  }));
});
