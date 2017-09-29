import { Component, Inject, Optional } from '@angular/core';
import { MD_DIALOG_DATA, MdDialogRef } from '@angular/material';

@Component({
  selector: 'app-static-modal',
  templateUrl: './static-modal.component.html',
  styleUrls: ['./static-modal.component.scss']
})
export class StaticModalComponent {

  constructor(public dialogRef: MdDialogRef<StaticModalComponent>,
              @Optional() @Inject(MD_DIALOG_DATA) public data: any) {
  }

}
