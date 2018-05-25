import { Directive, Input } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[appDisableControl]',
})
export class DisableControlDirective {
  @Input()
  public set disable(condition: boolean) {
    const action: string = condition ? 'disable' : 'enable';
    this._ngCtrl.control[action]();
  }

  constructor(private _ngCtrl: NgControl) {}
}
