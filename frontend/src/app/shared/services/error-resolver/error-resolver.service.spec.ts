import { TestBed, inject, async } from '@angular/core/testing';

import { MatDialog, MatDialogModule } from '@angular/material';

import { ErrorResolverService } from './error-resolver.service';
import { StaticModalComponent } from '../../components/static-modal/static-modal.component';
import { ErrorInfo } from '@shared/domain/error/error-info';

describe('ErrorResolverService', () => {
  const error: ErrorInfo = {
    url: 'localhost',
    message: 'Example error message',
  };
  const data: Object = {
    width: '250px',
    data: {
      text: 'Example error message',
      header: 'Error',
    },
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [StaticModalComponent],
      imports: [MatDialogModule],
      providers: [MatDialog, ErrorResolverService],
    });
  }));

  it('should be created', inject([ErrorResolverService], (service: ErrorResolverService) => {
    expect(service).toBeTruthy();
  }));

  it('should handle an error and open an alert', inject([ErrorResolverService], (service: ErrorResolverService) => {
    spyOn(service.dialog, 'open');
    service.handleError(error);

    expect(service.dialog.open).toHaveBeenCalledTimes(1);
    expect(service.dialog.open).toHaveBeenCalledWith(StaticModalComponent, data);
  }));

  it('should open an alert', inject([ErrorResolverService], (service: ErrorResolverService) => {
    spyOn(service.dialog, 'open');
    service.createAlert('Example error message');

    expect(service.dialog.open).toHaveBeenCalledTimes(1);
    expect(service.dialog.open).toHaveBeenCalledWith(StaticModalComponent, data);
  }));
});
