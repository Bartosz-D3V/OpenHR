import { TestBed, inject } from '@angular/core/testing';

import { MainGuard } from './main.guard';
import { RouterTestingModule } from '@angular/router/testing';

describe('MainGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        MainGuard,
      ],
      imports: [
        RouterTestingModule,
      ],
    });
  });

  it('should ...', inject([MainGuard], (guard: MainGuard) => {
    expect(guard).toBeTruthy();
  }));
});
