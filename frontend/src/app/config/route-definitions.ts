import { Routes } from '@angular/router';

import { MyDetailsComponent } from '../pages/my-details/my-details.component';
import { MyLeaveComponent } from '../pages/my-leave/my-leave.component';
import { DelegationComponent } from '../pages/delegation/delegation.component';

export const routeDefinitions: Routes = [
  {
    path: 'my-details',
    component: MyDetailsComponent,
    pathMatch: 'full',
  },
  {
    path: 'my-leave',
    component: MyLeaveComponent,
    pathMatch: 'full',
  },
  {
    path: 'delegation',
    component: DelegationComponent,
    pathMatch: 'full',
  },
];
