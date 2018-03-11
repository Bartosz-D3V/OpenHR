import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { NumberIndicatorComponent } from '@shared/components/number-indicator/number-indicator.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    FlexLayoutModule,
  ],
  exports: [
    CapitalizePipe,
    PageHeaderComponent,
    NumberIndicatorComponent,
  ],
  declarations: [
    CapitalizePipe,
    PageHeaderComponent,
    NumberIndicatorComponent,
  ],
})
export class SharedModule {
}
