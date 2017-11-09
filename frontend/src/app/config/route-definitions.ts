import { Routes } from '@angular/router';

import { PersonalDetailsComponent } from '../pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from '../pages/leave-application/leave-application.component';
import { DelegationComponent } from '../pages/delegation/delegation.component';
import { AboutComponent } from '../pages/about/about.component';

export const routeDefinitions: Routes = [
  {
    path: 'personal-details',
    component: PersonalDetailsComponent,
    pathMatch: 'full',
  },
  {
    path: 'leave-application',
    component: LeaveApplicationComponent,
    pathMatch: 'full',
  },
  {
    path: 'delegation',
    component: DelegationComponent,
    pathMatch: 'full',
  },
  {
    path: 'about',
    component: AboutComponent,
    pathMatch: 'full',
  },
];
