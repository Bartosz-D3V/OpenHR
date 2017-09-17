import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MdButtonModule, MdButtonToggleModule, MdCardModule, MdDialogModule, MdGridListModule, MdIconModule,
  MdInputModule, MdSidenavModule, MdSliderModule, MdSlideToggleModule, MdToolbarModule
} from '@angular/material';

import { AppComponent } from './app.component';
import { SidenavComponent } from './theme/components/sidenav/sidenav.component';
import { SidenavItemComponent } from './theme/components/sidenav/sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './theme/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { FlexLayoutModule } from '@angular/flex-layout';

import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    SidenavComponent,
    SidenavItemComponent,
    SidenavItemListComponent,
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
    FlexLayoutModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
