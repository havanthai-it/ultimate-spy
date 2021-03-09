import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReferralRoutingModule } from "./referral-routing.module";
import { ReferralComponent } from "./referral.component";
import { ReferralDashboardComponent } from './referral-dashboard/referral-dashboard.component';
import { ReferralIntroductionComponent } from './referral-introduction/referral-introduction.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from "@angular/material/paginator";
import { ReferralAgreementDialogComponent } from './referral-agreement-dialog/referral-agreement-dialog.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations: [
        ReferralComponent,
        ReferralDashboardComponent,
        ReferralIntroductionComponent,
        ReferralAgreementDialogComponent
    ],
    imports: [
        ReferralRoutingModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatTableModule,
        MatPaginatorModule
    ],
    providers: []
})
export class ReferralModule {}
