import { TestBed, inject, async } from '@angular/core/testing';
import { HttpModule } from '@angular/http';
import { Injectable } from '@angular/core';

import { MyDetailsService } from './my-details.service';
import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';

@Injectable()
class FakeErrorResolverService {
  public createAlert(error: any): void {
  }
}

describe('MyDetailsService', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
      ],
      providers: [
        MyDetailsService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    });
  }));

  it('should be created', inject([MyDetailsService], (service: MyDetailsService) => {
    expect(service).toBeTruthy();
  }));

});
