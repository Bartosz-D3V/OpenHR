import { Routes } from '@angular/router';

import { MainGuard } from '../shared/guards/main-guard/main.guard';
import { ManagerGuard } from '../shared/guards/manager-guard/manager.guard';
import { HrTeamMemberGuard } from '../shared/guards/hr-team-member-guard/hr-team-member.guard';
import { PersonalDetailsComponent } from '../modules/core/pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from '../modules/core/pages/leave-application/leave-application.component';
import { DelegationComponent } from '../modules/core/pages/delegation/delegation.component';
import { AboutComponent } from '../modules/core/pages/about/about.component';
import { CoreWrapperComponent } from '../modules/core/core-wrapper/core-wrapper.component';
import { AccountComponent } from '../modules/settings/pages/account/account.component';
import { SettingsComponent } from '../modules/settings/pages/settings/settings.component';
import { EmployeesComponent } from '../modules/core/pages/employees/employees.component';
import { AddEmployeeComponent } from '../modules/core/pages/add-employee/add-employee.component';
import { LoginComponent } from '../modules/landing/pages/login/login.component';
import { ManageLeaveApplicationsComponent } from '../modules/core/pages/manage-leave-applications/manage-leave-applications.component';
import { ManageEmployeesDataComponent } from '../modules/core/pages/manage-employees-data/manage-employees-data.component';
import { MyApplicationsComponent } from '../modules/core/pages/my-applications/my-applications.component';
import { DashboardComponent } from '../modules/core/pages/dashboard/dashboard.component';

export const routeDefinitions: Routes = [
  {
    path: 'app',
    component: CoreWrapperComponent,
    canActivate: [MainGuard],
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
        outlet: 'core',
      },
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
        path: 'my-applications',
        component: MyApplicationsComponent,
        outlet: 'core',
      },
      {
        path: 'employees',
        component: EmployeesComponent,
        outlet: 'core',
      },
      {
        path: 'manage-employee-data',
        component: ManageEmployeesDataComponent,
        outlet: 'core',
        canActivate: [ManagerGuard],
      },
      {
        path: 'add-employee',
        component: AddEmployeeComponent,
        outlet: 'core',
        canActivate: [HrTeamMemberGuard],
      },
      {
        path: 'manage-leave-applications',
        component: ManageLeaveApplicationsComponent,
        outlet: 'core',
        canActivate: [ManagerGuard],
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
