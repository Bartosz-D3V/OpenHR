import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { ServiceWorkerModule } from '@angular/service-worker';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import 'hammerjs/hammer';

import { SharedModule } from '@modules/shared/shared.module';
import { CoreModule } from '@modules/core/core.module';
import { LandingModule } from '@modules/landing/landing.module';
import { SettingsModule } from '@modules/settings/settings.module';
import { AppComponent } from '@boot/app.component';
import { StaticModalComponent } from '@shared/components/static-modal/static-modal.component';
import { FooterComponent } from '@shared/components/footer/footer.component';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { environment } from '../environments/environment';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [AppComponent, FooterComponent],
  imports: [
    ServiceWorkerModule.register('/ngsw-worker.js', { enabled: environment.production }),
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule,
    SharedModule,
    CoreModule,
    LandingModule,
    SettingsModule,
    AppRoutingModule,
  ],
  providers: [ErrorResolverService, NotificationService],
  bootstrap: [AppComponent],
  entryComponents: [StaticModalComponent],
})
export class AppModule {}
