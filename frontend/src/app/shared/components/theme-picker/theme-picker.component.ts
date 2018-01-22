import { ChangeDetectionStrategy, Component, ViewEncapsulation } from '@angular/core';

import { StyleManagerService } from '../../services/style-manager/style-manager.service';
import { ThemeStorageService } from './service/theme-storage.service';

@Component({
  selector: 'app-theme-picker',
  templateUrl: './theme-picker.component.html',
  styleUrls: ['./theme-picker.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None,
  providers: [
    ThemeStorageService,
    StyleManagerService,
  ],
})
export class ThemePickerComponent {

  constructor(private _themeStorageService: ThemeStorageService,
              private _styleManagerService: StyleManagerService) {
  }

}
