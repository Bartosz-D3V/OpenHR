import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  exports: [
    CapitalizePipe,
    PageHeaderComponent,
  ],
  declarations: [
    CapitalizePipe,
    PageHeaderComponent,
  ],
})
export class SharedModule {
}
