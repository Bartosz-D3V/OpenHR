import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { AsyncValidatorService } from '@shared/util/async-validators/service/async-validator.service';

describe('AsyncValidatorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AsyncValidatorService],
    });
  });

  it('should be created', inject([AsyncValidatorService], (service: AsyncValidatorService) => {
    expect(service).toBeTruthy();
  }));
});
