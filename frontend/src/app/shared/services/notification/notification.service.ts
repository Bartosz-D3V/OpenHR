import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';

@Injectable()
export class NotificationService {
  constructor(private _snackBar: MatSnackBar) {}

  public openSnackBar(message: string, action?: string): void {
    this._snackBar.open(message, action ? action : 'OK', {
      duration: 3500,
    });
  }
}
