import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MdAutocompleteModule, MdButtonModule, MdButtonToggleModule, MdCardModule, MdCheckboxModule, MdDatepickerModule,
  MdDialogModule, MdExpansionModule, MdGridListModule, MdIconModule, MdInputModule, MdListModule, MdNativeDateModule,
  MdRadioModule, MdSelectModule, MdSidenavModule, MdSliderModule, MdSlideToggleModule, MdStepperModule, MdTableModule,
  MdToolbarModule
} from '@angular/material';

import { CalendarModule } from 'primeng/primeng';

import { AppComponent } from './app.component';
import { SidenavComponent } from './shared/components/sidenav/sidenav.component';
import { SidenavItemComponent } from './shared/components/sidenav/sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';

import { PersonalDetailsComponent } from './pages/personal-details/personal-details.component';
import { PageHeaderComponent } from './shared/components/page-header/page-header.component';
import { CapitalizePipe } from './shared/pipes/capitalize/capitalize.pipe';
import { NamePipe } from './shared/pipes/name/name.pipe';
import { LeaveApplicationComponent } from './pages/leave-application/leave-application.component';
import { StaticModalComponent } from './shared/components/static-modal/static-modal.component';
import { ErrorResolverService } from './shared/services/error-resolver/error-resolver.service';
import { DelegationComponent } from './pages/delegation/delegation.component';
import { AppRoutingModule } from './app-routing.module';
import { AboutComponent } from './pages/about/about.component';
import { AvatarComponent } from './shared/components/avatar/avatar.component';
import { InitialsPipe } from './shared/pipes/initials/initials.pipe';

@NgModule({
  declarations: [
    AppComponent,
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
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    MdButtonModule,
    MdRadioModule,
    MdButtonToggleModule,
    MdCardModule,
    MdDialogModule,
    MdGridListModule,
    MdIconModule,
    MdListModule,
    MdInputModule,
    MdSidenavModule,
    MdSliderModule,
    MdSlideToggleModule,
    MdToolbarModule,
    MdDatepickerModule,
    MdNativeDateModule,
    MdExpansionModule,
    MdStepperModule,
    MdSelectModule,
    MdTableModule,
    MdAutocompleteModule,
    MdCheckboxModule,
    CalendarModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
  ],
  providers: [ErrorResolverService],
  bootstrap: [AppComponent],
  entryComponents: [StaticModalComponent],
})
export class AppModule {
}
