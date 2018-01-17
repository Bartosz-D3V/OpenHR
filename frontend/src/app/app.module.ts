import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import 'hammerjs/hammer';

import { CoreModule } from './modules/core/core.module';
import { LandingModule } from './modules/landing/landing.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './boot/app.component';
import { StaticModalComponent } from './shared/components/static-modal/static-modal.component';
import { ErrorResolverService } from './shared/services/error-resolver/error-resolver.service';
import { ThemePickerComponent } from './shared/components/theme-picker/theme-picker.component';

@NgModule({
  declarations: [
    AppComponent,
    ThemePickerComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule,
    CoreModule,
    LandingModule,
    AppRoutingModule,
  ],
  providers: [ErrorResolverService],
  bootstrap: [AppComponent],
  entryComponents: [StaticModalComponent],
})
export class AppModule {
}
