import { TestBed, inject, async } from '@angular/core/testing';

import { ErrorResolverService } from './error-resolver.service';
import { StaticModalComponent } from '../../components/static-modal/static-modal.component';
import { MdDialog, MdDialogModule } from '@angular/material';

describe('ErrorResolverService', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        StaticModalComponent
      ],
      imports: [
        MdDialogModule
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

});
