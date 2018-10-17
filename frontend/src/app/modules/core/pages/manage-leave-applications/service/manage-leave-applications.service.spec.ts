import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MatDialogModule } from '@angular/material';
import { MomentInput } from 'moment';

import { SystemVariables } from '@config/system-variables';
import { LeaveApplication } from '@shared/domain/application/leave-application';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ManageLeaveApplicationsService } from './manage-leave-applications.service';
import { Role } from '@shared/domain/subject/role';

describe('ManageLeaveApplicationsService', () => {
  let service: ManageLeaveApplicationsService;
  let http: HttpTestingController;
  const mockStartDate: MomentInput = '2020-05-05';
  const mockEndDate: MomentInput = '2020-05-10';
  const mockLeaveApplication1 = new LeaveApplication(null, mockStartDate, mockEndDate, false, false, false, null, null);
  const mockLeaveApplication2 = new LeaveApplication(null, mockStartDate, mockEndDate, false, false, false, null, null);
  const mockLeaveApplications: Array<LeaveApplication> = [mockLeaveApplication1, mockLeaveApplication2];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MatDialogModule, NoopAnimationsModule],
      providers: [JwtHelperService, ManageLeaveApplicationsService],
    });
    service = TestBed.get(ManageLeaveApplicationsService);
    http = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('API access methods', () => {
    const apiLink: string = SystemVariables.API_URL + '/leave-applications';

    it('should query current service URL', fakeAsync(() => {
      service.getAwaitingForActionLeaveApplications(45).subscribe();
      http.expectOne(`${apiLink}/awaiting?subjectId=45`);
    }));

    describe('getUnacceptedLeaveApplications', () => {
      it('should return an Observable of type Array of type LeaveApplication', fakeAsync(() => {
        let result: any;
        let error: any;
        service
          .getAwaitingForActionLeaveApplications(45)
          .subscribe((res: Array<LeaveApplication>) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/awaiting?subjectId=45`,
            method: 'GET',
          })
          .flush(mockLeaveApplications);

        tick();
        expect(error).toBeUndefined();
        expect(result).toBeDefined();
        expect(result.length).toEqual(mockLeaveApplications.length);
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        let result: Object;
        let error: any;
        service
          .getAwaitingForActionLeaveApplications(45)
          .subscribe((res: Array<LeaveApplication>) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/awaiting?subjectId=45`,
            method: 'GET',
          })
          .error(new ErrorEvent('404'));

        tick();
        expect(error).toBeDefined();
        expect(result).toBeUndefined();
      }));
    });

    describe('approveLeaveApplicationByManager', () => {
      beforeEach(() => {
        spyOn(service['_jwtHelper'], 'getUsersRole').and.returnValue([Role.MANAGER]);
      });

      it('send a PUT request to server with processInstanceId', fakeAsync(() => {
        let result: any;
        let error: any;
        service.approveLeaveApplicationByManager('45').subscribe((res: any) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/manager-approve?processInstanceId=45`,
            method: 'PUT',
          })
          .flush(null);

        tick();
        expect(error).toBeUndefined();
        expect(result).toBeDefined();
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        let result: Object;
        let error: any;
        service.approveLeaveApplicationByManager('45').subscribe((res: any) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/manager-approve?processInstanceId=45`,
            method: 'PUT',
          })
          .error(new ErrorEvent('404'));

        tick();
        expect(error).toBeDefined();
        expect(result).toBeUndefined();
      }));
    });

    describe('rejectLeaveApplicationByManager', () => {
      beforeEach(() => {
        spyOn(service['_jwtHelper'], 'getUsersRole').and.returnValue([Role.HRTEAMMEMBER]);
      });

      it('send a PUT request to server with processInstanceId', fakeAsync(() => {
        let result: any;
        let error: any;
        service.rejectLeaveApplicationByManager('45', null).subscribe((res: any) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/hr-reject?processInstanceId=45`,
            method: 'PUT',
          })
          .flush(null);

        tick();
        expect(error).toBeUndefined();
        expect(result).toBeDefined();
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        let result: Object;
        let error: any;
        service.rejectLeaveApplicationByManager('45', null).subscribe((res: any) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/hr-reject?processInstanceId=45`,
            method: 'PUT',
          })
          .error(new ErrorEvent('404'));

        tick();
        expect(error).toBeDefined();
        expect(result).toBeUndefined();
      }));
    });
  });
});
