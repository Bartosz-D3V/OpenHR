import { TestBed, inject, async } from '@angular/core/testing';

import { MatDialog, MatDialogModule } from '@angular/material';

import { ErrorResolverService } from './error-resolver.service';
import { StaticModalComponent } from '../../components/static-modal/static-modal.component';

describe('ErrorResolverService', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        StaticModalComponent,
      ],
      imports: [
        MatDialogModule,
      ],
      providers: [
        MatDialog,
        ErrorResolverService,
      ]
    });
  }));

  it('should be created', inject([ErrorResolverService], (service: ErrorResolverService) => {
    expect(service).toBeTruthy();
  }));

  it('should open an alert', inject([ErrorResolverService], (service: ErrorResolverService) => {
    const error: Object = {
      message: 'Example error message',
    };
    const data: Object = {
      width: '250px',
      data: {
        text: 'Example error message',
        header: 'Error',
      }
    };
    spyOn(service.dialog, 'open');
    service.createAlert(error);

    expect(service.dialog.open).toHaveBeenCalledTimes(1);
    expect(service.dialog.open).toHaveBeenCalledWith(StaticModalComponent, data);
  }));

});
