import { Routes } from '@angular/router';

import { PersonalDetailsComponent } from '../modules/core/pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from '../modules/core/pages/leave-application/leave-application.component';
import { DelegationComponent } from '../modules/core/pages/delegation/delegation.component';
import { AboutComponent } from '../modules/core/pages/about/about.component';
import { CoreWrapperComponent } from '../modules/core/core-wrapper/core-wrapper.component';
import { AccountComponent } from '../modules/core/pages/account/account.component';
import { SettingsComponent } from '../modules/core/pages/settings/settings.component';
import { EmployeesComponent } from '../modules/core/pages/employees/employees.component';
import { AddEmployeeComponent } from '../modules/core/pages/add-employee/add-employee.component';
import { LoginComponent } from '../modules/landing/pages/login/login.component';
import { MainGuard } from '../shared/guards/main-guard/main.guard';

export const routeDefinitions: Routes = [
  {
    path: 'app',
    component: CoreWrapperComponent,
    canActivate: [MainGuard],
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
        path: 'employees',
        component: EmployeesComponent,
        outlet: 'core',
      },
      {
        path: 'add-employee',
        component: AddEmployeeComponent,
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
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
  },
];
