import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReferralRoutingModule } from "./referral-routing.module";
import { ReferralComponent } from "./referral.component";
import { ReferralDashboardComponent } from './referral-dashboard/referral-dashboard.component';
import { ReferralIntroductionComponent } from './referral-introduction/referral-introduction.component';

@NgModule({
    declarations: [
        ReferralComponent,
        ReferralDashboardComponent,
        ReferralIntroductionComponent
    ],
    imports: [
        ReferralRoutingModule,
        CommonModule
    ],
    providers: []
})
export class ReferralModule {}
