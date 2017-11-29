import { Routes } from '@angular/router';

import { LandingComponent } from '../modules/landing/pages/landing/landing.component';
import { PersonalDetailsComponent } from '../modules/core/pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from '../modules/core/pages/leave-application/leave-application.component';
import { DelegationComponent } from '../modules/core/pages/delegation/delegation.component';
import { AboutComponent } from '../modules/core/pages/about/about.component';
import { CoreWrapperComponent } from '../modules/core/core-wrapper/core-wrapper.component';
import { AccountComponent } from '../modules/core/pages/account/account.component';
import { SettingsComponent } from '../modules/core/pages/settings/settings.component';

export const routeDefinitions: Routes = [
  {
    path: 'app',
    component: CoreWrapperComponent,
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
      {
        path: 'account',
        component: AccountComponent,
        outlet: 'core',
      },
      {
        path: 'settings',
        component: SettingsComponent,
        outlet: 'core',
      },
    ],
  },
  {
    path: '',
    redirectTo: 'welcome',
    pathMatch: 'full',
  },
  {
    path: 'welcome',
    component: LandingComponent,
    pathMatch: 'full',
  },
];
