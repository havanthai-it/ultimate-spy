import { CommonModule, DatePipe } from "@angular/common";
import { NgModule } from "@angular/core";
import { DashboardRoutingModule } from "./dashboard-routing.module";
import { DashboardComponent } from "./dashboard.component";
import { DashboardProfileComponent } from "./dashboard-profile/dashboard-profile.component";
import { DashboardPlanComponent } from "./dashboard-plan/dashboard-plan.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations: [
        DashboardComponent,
        DashboardProfileComponent,
        DashboardPlanComponent
    ],
    imports: [
        DashboardRoutingModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    providers: [DatePipe]
})
export class DashboardModule {}
