import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {
  MatAutocompleteModule, MatMenuModule, MatButtonModule, MatRadioModule, MatButtonToggleModule, MatCardModule,
  MatDialogModule, MatGridListModule, MatIconModule, MatListModule, MatInputModule, MatSidenavModule, MatSliderModule,
  MatSlideToggleModule, MatToolbarModule, MatFormFieldModule, MatDatepickerModule, MatNativeDateModule,
  MatExpansionModule, MatStepperModule, MatSelectModule, MatTableModule, MatCheckboxModule,
} from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';

import 'hammerjs/hammer';

import { InitialsPipe } from '../../shared/pipes/initials/initials.pipe';
import { NamePipe } from '../../shared/pipes/name/name.pipe';
import { SidenavComponent } from '../../shared/components/sidenav/sidenav.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { DateRangeComponent } from '../../shared/components/date-range/date-range.component';
import { AvatarComponent } from '../../shared/components/avatar/avatar.component';
import { StaticModalComponent } from '../../shared/components/static-modal/static-modal.component';
import { SidenavItemListComponent } from '../../shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { PersonalDetailsComponent } from './pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from './pages/leave-application/leave-application.component';
import { AboutComponent } from './pages/about/about.component';
import { DelegationComponent } from './pages/delegation/delegation.component';
import { CoreWrapperComponent } from './core-wrapper/core-wrapper.component';
import { AccountComponent } from './pages/account/account.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { AppRoutingModule } from '../../app-routing.module';
import { EmployeesComponent } from './pages/employees/employees.component';

@NgModule({
  imports: [
    CommonModule,
    FlexLayoutModule,
    MatMenuModule,
    MatButtonModule,
    MatRadioModule,
    MatButtonToggleModule,
    MatCardModule,
    MatDialogModule,
    MatGridListModule,
    MatIconModule,
    MatListModule,
    MatInputModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatStepperModule,
    MatSelectModule,
    MatTableModule,
    MatAutocompleteModule,
    MatCheckboxModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    AppRoutingModule,
  ],
  declarations: [
    SidenavComponent,
    SidenavItemListComponent,
    PersonalDetailsComponent,
    PageHeaderComponent,
    CapitalizePipe,
    NamePipe,
    LeaveApplicationComponent,
    StaticModalComponent,
    DelegationComponent,
    DateRangeComponent,
    AboutComponent,
    AvatarComponent,
    InitialsPipe,
    CoreWrapperComponent,
    AccountComponent,
    SettingsComponent,
    EmployeesComponent,
  ],
})
export class CoreModule {
}
