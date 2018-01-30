import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { routeDefinitions } from './config/route-definitions';
import { MainGuard } from './shared/guards/main-guard/main.guard';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routeDefinitions),
  ],
  exports: [
    RouterModule,
  ],
  providers: [
    MainGuard,
  ],
})
export class AppRoutingModule {
}
