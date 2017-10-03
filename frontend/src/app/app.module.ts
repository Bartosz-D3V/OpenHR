import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MdButtonModule, MdButtonToggleModule, MdCardModule, MdDatepickerModule, MdDialogModule, MdExpansionModule,
  MdGridListModule, MdIconModule, MdInputModule, MdMenuModule, MdNativeDateModule, MdSidenavModule, MdSliderModule,
  MdSlideToggleModule, MdStepperModule, MdToolbarModule
} from '@angular/material';

import { AppComponent } from './app.component';
import { SidenavComponent } from './shared/components/sidenav/sidenav.component';
import { SidenavItemComponent } from './shared/components/sidenav/sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';

import { MyDateRangePickerModule } from 'mydaterangepicker';

import { MyDetailsComponent } from './pages/my-details/my-details.component';
import { PageHeaderComponent } from './shared/components/page-header/page-header.component';
import { CapitalizePipe } from './shared/pipes/capitalize/capitalize.pipe';
import { NamePipe } from './shared/pipes/name/name.pipe';
import { MyLeaveComponent } from './pages/my-leave/my-leave.component';
import { StaticModalComponent } from './shared/components/static-modal/static-modal.component';
import { ErrorResolverService } from './shared/services/error-resolver/error-resolver.service';
import { DropdownComponent } from './shared/components/dropdown/dropdown.component';

@NgModule({
  declarations: [
    AppComponent,
    SidenavComponent,
    SidenavItemComponent,
    SidenavItemListComponent,
    MyDetailsComponent,
    PageHeaderComponent,
    CapitalizePipe,
    NamePipe,
    MyLeaveComponent,
    StaticModalComponent,
    DropdownComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    MdButtonModule,
    MdButtonToggleModule,
    MdCardModule,
    MdDialogModule,
    MdGridListModule,
    MdIconModule,
    MdInputModule,
    MdSidenavModule,
    MdSliderModule,
    MdSlideToggleModule,
    MdToolbarModule,
    MdDatepickerModule,
    MdNativeDateModule,
    MdExpansionModule,
    MdStepperModule,
    MdMenuModule,
    MyDateRangePickerModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      {
        path: 'my-details',
        component: MyDetailsComponent,
        pathMatch: 'full',
      },
      {
        path: 'my-leave',
        component: MyLeaveComponent,
        pathMatch: 'full',
      }
    ]),
  ],
  providers: [ErrorResolverService],
  bootstrap: [AppComponent],
  entryComponents: [StaticModalComponent],
})
export class AppModule {
}
