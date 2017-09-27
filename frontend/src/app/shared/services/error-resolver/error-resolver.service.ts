import { Injectable } from '@angular/core';
import { MdDialog } from '@angular/material/dialog';

import { StaticModalComponent } from '../../components/static-modal/static-modal.component';

@Injectable()
export class ErrorResolverService {

  constructor(public dialog: MdDialog) {
  }

  public createAlert(error: any): void {
    const dialogRef = this.dialog.open(StaticModalComponent, {
      width: '250px',
      data: {
        error: error
      },
    });
  }

}
