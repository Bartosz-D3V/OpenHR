import { Directive, Input } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[appResetControl]',
})
export class ResetControlDirective {
  @Input()
  public set reset(condition: boolean) {
    if (condition) {
      this._ngCtrl.control.reset();
    }
  }

  constructor(private _ngCtrl: NgControl) {}
}
