import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {
  MatAutocompleteModule,
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
  MatCheckboxModule,
} from '@angular/material';

import { CalendarModule } from 'primeng/primeng';

import { InitialsPipe } from '../../shared/pipes/initials/initials.pipe';
import { NamePipe } from '../../shared/pipes/name/name.pipe';
import { SidenavComponent } from '../../shared/components/sidenav/sidenav.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { AvatarComponent } from '../../shared/components/avatar/avatar.component';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { StaticModalComponent } from '../../shared/components/static-modal/static-modal.component';
import { SidenavItemListComponent } from '../../shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { SidenavItemComponent } from '../../shared/components/sidenav/sidenav-item/sidenav-item.component';
import { PersonalDetailsComponent } from './pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from './pages/leave-application/leave-application.component';
import { AboutComponent } from './pages/about/about.component';
import { DelegationComponent } from './pages/delegation/delegation.component';
import { CoreWrapperComponent } from './core-wrapper/core-wrapper.component';
import { AppRoutingModule } from '../../app-routing.module';

@NgModule({
  imports: [
    CommonModule,
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
    CalendarModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    AppRoutingModule,
  ],
  declarations: [
    SidenavComponent,
    SidenavItemComponent,
    SidenavItemListComponent,
    PersonalDetailsComponent,
    PageHeaderComponent,
    CapitalizePipe,
    NamePipe,
    LeaveApplicationComponent,
    StaticModalComponent,
    DelegationComponent,
    AboutComponent,
    AvatarComponent,
    InitialsPipe,
    CoreWrapperComponent,
  ],
})
export class CoreModule {
}
