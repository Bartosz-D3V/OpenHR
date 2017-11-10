import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { routeDefinitions } from './config/route-definitions';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routeDefinitions, { enableTracing: true }),
  ],
  exports: [
    RouterModule,
  ]
})
export class AppRoutingModule {
}
