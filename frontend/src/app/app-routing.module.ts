import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { routeDefinitions } from '@config/route-definitions';
import { MainGuard } from '@shared/guards/main-guard/main.guard';
import { ManagerGuard } from '@shared/guards/manager-guard/manager.guard';
import { HrTeamMemberGuard } from '@shared/guards/hr-team-member-guard/hr-team-member.guard';

@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routeDefinitions)],
  exports: [RouterModule],
  providers: [MainGuard, ManagerGuard, HrTeamMemberGuard],
})
export class AppRoutingModule {}
