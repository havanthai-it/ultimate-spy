import { CommonModule, DatePipe } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CheckoutRoutingModule } from "./checkout-routing.module";
import { CheckoutComponent } from "./checkout.component";

@NgModule({
    declarations: [
        CheckoutComponent
    ],
    imports: [
        CheckoutRoutingModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [DatePipe]
})
export class CheckoutModule {}
