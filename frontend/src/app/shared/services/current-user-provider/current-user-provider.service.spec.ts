import { TestBed } from '@angular/core/testing';

import { CurrentUserProviderService } from './current-user-provider.service';

describe('CurrentUserProviderService', () => {
  let service: CurrentUserProviderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        CurrentUserProviderService,
      ],
    });
    service = TestBed.get(CurrentUserProviderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
