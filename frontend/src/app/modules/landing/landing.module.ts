import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import 'hammerjs/hammer';

import { LoginComponent } from './pages/login/login.component';

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    LoginComponent,
  ],
})
export class LandingModule {
}
