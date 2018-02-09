import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { StaticModalComponent } from '../../components/static-modal/static-modal.component';

@Injectable()
export class ErrorResolverService {

  private dialogOpened = false;
  private readonly header = 'Error';

  constructor(public dialog: MatDialog) {
  }

  private subscribeToEvents(): void {
    this.dialog.afterAllClosed
      .subscribe(() => this.dialogOpened = false);
    this.dialog.afterOpen
      .subscribe(() => this.dialogOpened = true);
  }

  public handleError(error: any): void {
    this.createAlert(error);
  }

  public createAlert(error: any): void {
    if (!this.dialogOpened) {
      const dialogRef = this.dialog.open(StaticModalComponent, {
        width: '250px',
        data: {
          text: error.message,
          header: this.header,
        },
      });
    }
    this.subscribeToEvents();
  }

}
