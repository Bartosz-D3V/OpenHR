import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { NumberIndicatorComponent } from '@shared/components/number-indicator/number-indicator.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { DisableControlDirective } from '@shared/directives/disable-control/disable-control.directive';
import { ResetControlDirective } from '@shared/directives/reset-control/reset-control.directive';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, FlexLayoutModule],
  exports: [CapitalizePipe, PageHeaderComponent, NumberIndicatorComponent, DisableControlDirective, ResetControlDirective],
  declarations: [CapitalizePipe, PageHeaderComponent, NumberIndicatorComponent, DisableControlDirective, ResetControlDirective],
})
export class SharedModule {}
