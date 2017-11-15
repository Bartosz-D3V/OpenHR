import { Routes } from '@angular/router';

import { PersonalDetailsComponent } from '../modules/core/pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from '../modules/core/pages/leave-application/leave-application.component';
import { DelegationComponent } from '../modules/core/pages/delegation/delegation.component';
import { AboutComponent } from '../modules/core/pages/about/about.component';
import { LandingComponent } from '../modules/landing/pages/landing/landing.component';
import { CoreComponent } from '../modules/core/core/core.component';

export const routeDefinitions: Routes = [
  {
    path: 'app',
    component: CoreComponent,
    children: [
      {
        path: 'personal-details',
        component: PersonalDetailsComponent,
        outlet: 'core',
      },
      {
        path: 'leave-application',
        component: LeaveApplicationComponent,
        outlet: 'core',
      },
      {
        path: 'delegation',
        component: DelegationComponent,
        outlet: 'core',
      },
      {
        path: 'about',
        component: AboutComponent,
        outlet: 'core',
      },
    ]
  },
  {
    path: '',
    redirectTo: 'welcome',
    pathMatch: 'full'
  },
  {
    path: 'welcome',
    component: LandingComponent,
    pathMatch: 'full',
  }
];
