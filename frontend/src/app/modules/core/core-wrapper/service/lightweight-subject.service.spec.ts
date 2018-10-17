import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MatDialogModule } from '@angular/material';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { LightweightSubjectService } from './lightweight-subject.service';

describe('LightweightSubjectService', () => {
  let http: HttpTestingController;
  let service: LightweightSubjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LightweightSubjectService, JwtHelperService],
      imports: [HttpClientTestingModule, MatDialogModule],
    });
    service = TestBed.get(LightweightSubjectService);
    http = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('API Access method', () => {
    const apiLink: string = SystemVariables.API_URL + '/subjects';
    const mockUser: LightweightSubject = new LightweightSubject(1, 'John', 'Black', 'Tester');

    describe('getCurrentSubject', () => {
      it('should query current service URL', fakeAsync(() => {
        service.getUser(1).subscribe();

        http.expectOne(`${apiLink}/lightweight/1`);
      }));

      it('should return an Observable of type Subject', fakeAsync(() => {
        let result: Object;
        let error: any;
        service.getUser(1).subscribe((res: Object) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/lightweight/1`,
            method: 'GET',
          })
          .flush(mockUser);
        tick();

        expect(error).toBeUndefined();
        expect(typeof result).toBe('object');
        expect(JSON.stringify(result)).toEqual(JSON.stringify(mockUser));
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        let result: Object;
        let error: any;
        service.getUser(1).subscribe((res: Object) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/lightweight/1`,
            method: 'GET',
          })
          .error(new ErrorEvent('404'));
        tick();
      }));
    });
  });
});
