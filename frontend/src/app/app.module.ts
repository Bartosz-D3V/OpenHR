import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MdSidenavModule } from '@angular/material';

import { AppComponent } from './app.component';
import { SidenavComponent } from './theme/components/sidenav/sidenav.component';
import { SidenavItemComponent } from './theme/components/sidenav/sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './theme/components/sidenav/sidenav-item-list/sidenav-item-list.component';

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
    MdSidenavModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
