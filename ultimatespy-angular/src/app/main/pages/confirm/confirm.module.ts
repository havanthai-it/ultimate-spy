import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ConfirmRoutingModule } from "./confirm-routing.module";
import { ConfirmComponent } from "./confirm.component";

@NgModule({
    declarations: [
        ConfirmComponent
    ],
    imports: [
        ConfirmRoutingModule,
        CommonModule
    ],
    providers: []
})
export class ConfirmModule {}
