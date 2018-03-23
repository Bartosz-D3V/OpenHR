import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { JwtHelperService } from '@shared//services/jwt/jwt-helper.service';
import { MyApplicationsService } from './my-applications.service';

describe('MyApplicationsService', () => {
  let service: MyApplicationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MyApplicationsService, JwtHelperService],
      imports: [HttpClientTestingModule],
    });
    service = TestBed.get(MyApplicationsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
