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
  MatPaginatorModule,
  MatTableModule,
  MatCheckboxModule,
  MatSnackBarModule,
  MatProgressSpinnerModule,
  MatTooltipModule,
} from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import 'hammerjs/hammer';

import { AppRoutingModule } from '../../app-routing.module';
import { InitialsPipe } from '@shared/pipes/initials/initials.pipe';
import { NamePipe } from '@shared/pipes/name/name.pipe';
import { ApplicationStatusPipe } from '@modules/core/pages/my-applications/pipe/leave-application-status/application-status.pipe';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { SidenavComponent } from '@shared/components/sidenav/sidenav.component';
import { DateRangeComponent } from '@shared/components/date-range/date-range.component';
import { AvatarComponent } from '@shared/components/avatar/avatar.component';
import { StaticModalComponent } from '@shared/components/static-modal/static-modal.component';
import { SidenavItemListComponent } from '@shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { SharedModule } from '@modules/shared/shared.module';
import { TokenExpirationModalComponent } from '@shared/components/token-expiration-modal/token-expiration-modal.component';
import { PersonalDetailsComponent } from './pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from './pages/leave-application/leave-application.component';
import { AboutComponent } from './pages/about/about.component';
import { DelegationComponent } from './pages/delegation/delegation.component';
import { CoreWrapperComponent } from './core-wrapper/core-wrapper.component';
import { WorkersComponent } from './pages/workers/workers.component';
import { AddEmployeeComponent } from './pages/add-employee/add-employee.component';
import { ManageLeaveApplicationsComponent } from './pages/manage-leave-applications/manage-leave-applications.component';
import { ManageEmployeesDataComponent } from './pages/manage-employees-data/manage-employees-data.component';
import { MyApplicationsComponent } from './pages/my-applications/my-applications.component';
import { ManageDelegationsComponent } from './pages/manage-delegations/manage-delegations.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ApplicationTypePipe } from './pages/my-applications/pipe/application-type/application-type.pipe';

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
    MatPaginatorModule,
    MatTableModule,
    MatAutocompleteModule,
    MatCheckboxModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    AppRoutingModule,
  ],
  declarations: [
    SidenavComponent,
    SidenavItemListComponent,
    PersonalDetailsComponent,
    NamePipe,
    LeaveApplicationComponent,
    StaticModalComponent,
    DelegationComponent,
    DateRangeComponent,
    AboutComponent,
    AvatarComponent,
    InitialsPipe,
    CoreWrapperComponent,
    WorkersComponent,
    AddEmployeeComponent,
    ManageLeaveApplicationsComponent,
    ManageEmployeesDataComponent,
    MyApplicationsComponent,
    ApplicationStatusPipe,
    DashboardComponent,
    ManageDelegationsComponent,
    ApplicationTypePipe,
    TokenExpirationModalComponent,
  ],
  providers: [JwtHelperService],
  entryComponents: [TokenExpirationModalComponent],
})
export class CoreModule {}
