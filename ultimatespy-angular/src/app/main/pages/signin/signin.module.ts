import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SigninRoutingModule } from "./signin-routing.module";
import { SigninComponent } from "./signin.component";

@NgModule({
    declarations: [
        SigninComponent
    ],
    imports: [
        SigninRoutingModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    providers: []
})
export class SigninModule {}
