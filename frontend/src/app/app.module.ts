import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MdButtonModule, MdButtonToggleModule, MdCardModule, MdDatepickerModule, MdDialogModule, MdExpansionModule,
  MdGridListModule,
  MdIconModule,
  MdInputModule, MdNativeDateModule, MdSidenavModule, MdSliderModule, MdSlideToggleModule, MdToolbarModule
} from '@angular/material';

import { AppComponent } from './app.component';
import { SidenavComponent } from './shared/components/sidenav/sidenav.component';
import { SidenavItemComponent } from './shared/components/sidenav/sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';

import { RouterModule } from '@angular/router';
import { MyDetailsComponent } from './pages/my-details/my-details.component';
import { PageHeaderComponent } from './shared/components/page-header/page-header.component';
import { CapitalizePipe } from './shared/pipes/capitalize/capitalize.pipe';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NamePipe } from './shared/pipes/name/name.pipe';

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
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
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
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      {
        path: 'my-details',
        component: MyDetailsComponent,
        pathMatch: 'full'
      },

    ]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
