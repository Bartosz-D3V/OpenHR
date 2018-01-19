import { ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation } from '@angular/core';

import { ThemeStorageService } from './service/theme-storage.service';

@Component({
  selector: 'app-theme-picker',
  templateUrl: './theme-picker.component.html',
  styleUrls: ['./theme-picker.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None,
})
export class ThemePickerComponent implements OnInit {

  constructor(private _themeStorageService: ThemeStorageService) { }

  ngOnInit() {
  }

}
