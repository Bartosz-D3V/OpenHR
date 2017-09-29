import { TestBed, inject, async } from '@angular/core/testing';

import { MdDialog, MdDialogModule } from '@angular/material';

import { ErrorResolverService } from './error-resolver.service';
import { StaticModalComponent } from '../../components/static-modal/static-modal.component';

describe('ErrorResolverService', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        StaticModalComponent,
      ],
      imports: [
        MdDialogModule,
      ],
      providers: [
        MdDialog,
        ErrorResolverService,
      ]
    });
  }));

  it('should be created', inject([ErrorResolverService], (service: ErrorResolverService) => {
    expect(service).toBeTruthy();
  }));

  it('should open an alert', inject([ErrorResolverService], (service: ErrorResolverService) => {
    const mockError = 'Example error message';
    const data: Object = {
      width: '250px',
      data: {
        error: mockError,
      }
    };
    spyOn(service.dialog, 'open');
    service.createAlert(mockError);

    expect(service.dialog.open).toHaveBeenCalledTimes(1);
    expect(service.dialog.open).toHaveBeenCalledWith(StaticModalComponent, data);
  }));

});
