import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ErrorInfo } from '@shared/domain/error/error-info';
import { StaticModalComponent } from '../../components/static-modal/static-modal.component';
import { MatDialogRef } from '@angular/material';

@Injectable()
export class ErrorResolverService {
  private dialogOpened = false;
  private readonly header = 'Error';

  constructor(public dialog: MatDialog) {}

  private subscribeToEvents(): void {
    this.dialog.afterAllClosed.subscribe(() => (this.dialogOpened = false));
    this.dialog.afterOpen.subscribe(() => (this.dialogOpened = true));
  }

  public handleError(errorInfo: ErrorInfo): void {
    this.createAlert(errorInfo.message);
  }

  public createAlert(message: string, header?: string): void {
    if (!this.dialogOpened) {
      const dialogRef: MatDialogRef<StaticModalComponent> = this.dialog.open(StaticModalComponent, {
        width: '250px',
        data: {
          text: message,
          header: !header ? this.header : header,
        },
      });
    }
    this.subscribeToEvents();
  }
}
