import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatGridListModule, MatIconModule, MatMenuModule } from '@angular/material';

import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { NumberIndicatorComponent } from '@shared/components/number-indicator/number-indicator.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { ThemePickerComponent } from '@shared/components/theme-picker/theme-picker.component';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, FlexLayoutModule, MatIconModule, MatGridListModule, MatMenuModule],
  exports: [CapitalizePipe, PageHeaderComponent, NumberIndicatorComponent, ThemePickerComponent],
  declarations: [CapitalizePipe, PageHeaderComponent, NumberIndicatorComponent, ThemePickerComponent],
})
export class SharedModule {}
