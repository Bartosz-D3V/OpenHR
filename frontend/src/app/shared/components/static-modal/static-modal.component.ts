import { Component, Inject, Optional } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-static-modal',
  templateUrl: './static-modal.component.html',
  styleUrls: ['./static-modal.component.scss'],
})
export class StaticModalComponent {
  constructor(
    public dialogRef: MatDialogRef<StaticModalComponent>,
    @Optional()
    @Inject(MAT_DIALOG_DATA)
    public data: any
  ) {}

  public close(): void {
    this.dialogRef.close();
  }
}
