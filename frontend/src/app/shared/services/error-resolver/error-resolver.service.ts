import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { StaticModalComponent } from '../../components/static-modal/static-modal.component';

@Injectable()
export class ErrorResolverService {

  private header = 'Error';

  constructor(public dialog: MatDialog) {
  }

  public handleError(error: any): void {
    console.log('An error occurred', error);
    this.createAlert(error);
  }

  public createAlert(error: any): void {
    const dialogRef = this.dialog.open(StaticModalComponent, {
      width: '250px',
      data: {
        text: error.message,
        header: this.header,
      },
    });
  }

}
