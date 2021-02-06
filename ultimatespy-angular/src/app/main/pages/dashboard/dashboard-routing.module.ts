import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../../../core/guards/AuthGuard';
import { DashboardPlanComponent } from './dashboard-plan/dashboard-plan.component';
import { DashboardProfileComponent } from './dashboard-profile/dashboard-profile.component';
import { DashboardComponent } from './dashboard.component';

const routes: Routes = [
    {
        path: '',
        component: DashboardComponent,
        children: [
          { path: 'profile', component: DashboardProfileComponent, pathMatch: 'full', canActivate: [AuthGuard] },
          { path: 'plan', component: DashboardPlanComponent, pathMatch: 'full', canActivate: [AuthGuard] }
        ]
      }
]

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule
    ]
})

export class DashboardRoutingModule {}
