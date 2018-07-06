import { Routes } from '@angular/router';

import { MainGuard } from '@shared//guards/main-guard/main.guard';
import { ManagerGuard } from '@shared//guards/manager-guard/manager.guard';
import { PersonalDetailsComponent } from '@modules/core/pages/personal-details/personal-details.component';
import { LeaveApplicationComponent } from '@modules/core/pages/leave-application/leave-application.component';
import { DelegationComponent } from '@modules/core/pages/delegation/delegation.component';
import { AboutComponent } from '@modules/core/pages/about/about.component';
import { CoreWrapperComponent } from '@modules/core/core-wrapper/core-wrapper.component';
import { AccountComponent } from '@modules/settings/pages/account/account.component';
import { SettingsComponent } from '@modules/settings/pages/settings/settings.component';
import { WorkersComponent } from '@modules/core/pages/workers/workers.component';
import { AddEmployeeComponent } from '@modules/core/pages/add-employee/add-employee.component';
import { LoginComponent } from '@modules/landing/pages/login/login.component';
import { ManageLeaveApplicationsComponent } from '@modules/core/pages/manage-leave-applications/manage-leave-applications.component';
import { ManageWorkersDataComponent } from '@modules/core/pages/manage-workers-data/manage-workers-data.component';
import { MyApplicationsComponent } from '@modules/core/pages/my-applications/my-applications.component';
import { DashboardComponent } from '@modules/core/pages/dashboard/dashboard.component';
import { ManageDelegationsComponent } from '@modules/core/pages/manage-delegations/manage-delegations.component';
import { AdminComponent } from '@modules/settings/pages/admin/admin.component';

export const routeDefinitions: Routes = [
  {
    path: 'app',
    component: CoreWrapperComponent,
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
        pathMatch: 'full',
      },
      {
        path: 'personal-details',
        component: PersonalDetailsComponent,
        outlet: 'core',
        pathMatch: 'full',
      },
      {
        path: 'leave-application',
        component: LeaveApplicationComponent,
        outlet: 'core',
        pathMatch: 'full',
      },
      {
        path: 'delegation',
        component: DelegationComponent,
        outlet: 'core',
        pathMatch: 'full',
      },
      {
        path: 'delegation/:id',
        component: DelegationComponent,
        outlet: 'core',
        pathMatch: 'full',
      },
      {
        path: 'my-applications',
        component: MyApplicationsComponent,
        outlet: 'core',
        pathMatch: 'full',
      },
      {
        path: 'employees',
        component: WorkersComponent,
        outlet: 'core',
        pathMatch: 'full',
      },
      {
        path: 'manage-workers-data',
        component: ManageWorkersDataComponent,
        outlet: 'core',
        canActivate: [ManagerGuard],
        pathMatch: 'full',
      },
      {
        path: 'add-employee',
        component: AddEmployeeComponent,
        outlet: 'core',
        canActivate: [ManagerGuard],
        pathMatch: 'full',
      },
      {
        path: 'manage-leave-applications',
        component: ManageLeaveApplicationsComponent,
        outlet: 'core',
        canActivate: [ManagerGuard],
        pathMatch: 'full',
      },
      {
        path: 'manage-delegation-applications',
        component: ManageDelegationsComponent,
        outlet: 'core',
        canActivate: [ManagerGuard],
        pathMatch: 'full',
      },
      {
        path: 'about',
        component: AboutComponent,
        outlet: 'core',
        pathMatch: 'full',
      },
      {
        path: 'account',
        component: AccountComponent,
        outlet: 'core',
        pathMatch: 'full',
      },
      {
        path: 'settings',
        component: SettingsComponent,
        outlet: 'core',
        pathMatch: 'full',
      },
      {
        path: 'admin',
        component: AdminComponent,
        outlet: 'core',
        pathMatch: 'full',
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
