import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-number-indicator',
  templateUrl: './number-indicator.component.html',
  styleUrls: ['./number-indicator.component.scss'],
})
export class NumberIndicatorComponent {
  @Input()
  public number: number;

  @Input()
  public description: string;
}
