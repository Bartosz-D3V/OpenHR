import { Routes } from '@angular/router';

import { PersonalDetailsComponent } from '../pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from '../pages/leave-application/leave-application.component';
import { DelegationComponent } from '../pages/delegation/delegation.component';
import { AboutComponent } from '../pages/about/about.component';
import { LandingComponent } from '../pages/landing/landing.component';
import { CoreComponent } from '../pages/core/core.component';

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
