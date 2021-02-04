import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { PrivacyRoutingModule } from "./privacy-routing.module";
import { PrivacyComponent } from "./privacy.component";

@NgModule({
    declarations: [
        PrivacyComponent
    ],
    imports: [
        PrivacyRoutingModule,
        CommonModule
    ],
    providers: []
})
export class PrivacyModule {}
