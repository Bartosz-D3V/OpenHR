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

  it('should be created', inject([StyleManagerService], (service: StyleManagerService) => {
    expect(service).toBeTruthy();
  }));
});
