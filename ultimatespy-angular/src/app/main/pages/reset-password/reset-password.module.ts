import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ResetPasswordRoutingModule } from "./reset-password-routing.module";
import { ResetPasswordComponent } from "./reset-password.component";

@NgModule({
    declarations: [
        ResetPasswordComponent
    ],
    imports: [
        ResetPasswordRoutingModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    providers: []
})
export class ResetPasswordModule {}
