import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {
  MatCardModule,
  MatFormFieldModule,
  MatSlideToggleModule,
  MatToolbarModule,
  MatInputModule,
  MatButtonModule,
  MatIconModule,
  MatTabsModule,
  MatProgressSpinnerModule,
  MatDatepickerModule,
} from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import 'hammerjs/hammer';

import { SharedModule } from '@modules/shared/shared.module';
import { AppRoutingModule } from '../../app-routing.module';
import { SettingsComponent } from './pages/settings/settings.component';
import { AccountComponent } from './pages/account/account.component';
import { AdminComponent } from './pages/admin/admin.component';
import { AllowanceSettingsComponent } from './pages/admin/components/allowance-settings/allowance-settings.component';

@NgModule({
  imports: [
    CommonModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatCardModule,
    MatSlideToggleModule,
    SharedModule,
    AppRoutingModule,
    MatIconModule,
    MatTabsModule,
    MatProgressSpinnerModule,
    MatDatepickerModule,
  ],
  declarations: [AccountComponent, SettingsComponent, AdminComponent, AllowanceSettingsComponent],
})
export class SettingsModule {}
