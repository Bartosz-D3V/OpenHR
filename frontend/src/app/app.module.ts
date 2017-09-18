import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MdButtonModule, MdButtonToggleModule, MdCardModule, MdDialogModule, MdGridListModule, MdIconModule,
  MdInputModule, MdSidenavModule, MdSliderModule, MdSlideToggleModule, MdToolbarModule
} from '@angular/material';

import { AppComponent } from './app.component';
import { SidenavComponent } from './shared/components/sidenav/sidenav.component';
import { SidenavItemComponent } from './shared/components/sidenav/sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { FlexLayoutModule } from '@angular/flex-layout';

import { RouterModule } from '@angular/router';
import { MyDetailsComponent } from './pages/my-details/my-details.component';

@NgModule({
  declarations: [
    AppComponent,
    SidenavComponent,
    SidenavItemComponent,
    SidenavItemListComponent,
    MyDetailsComponent,
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
    FlexLayoutModule,
    RouterModule.forRoot([
      {
        path: '',
        redirectTo: '',
        pathMatch: 'full'
      },

    ]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
