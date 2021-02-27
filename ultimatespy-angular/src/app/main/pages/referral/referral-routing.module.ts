import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/core/guards/AuthGuard';
import { ReferralDashboardComponent } from './referral-dashboard/referral-dashboard.component';
import { ReferralIntroductionComponent } from './referral-introduction/referral-introduction.component';
import { ReferralComponent } from './referral.component';

const routes: Routes = [
    {
        path: '',
        component: ReferralComponent,
        children: [
            { path: '', redirectTo: 'introduction', pathMatch: 'full' },
            { path: 'introduction', component: ReferralIntroductionComponent },
            { path: 'dashboard', component: ReferralDashboardComponent, canActivate: [AuthGuard] },
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

export class ReferralRoutingModule {}
