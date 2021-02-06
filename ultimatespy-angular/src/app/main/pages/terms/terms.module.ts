import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { TermsRoutingModule } from "./terms-routing.module";
import { TermsComponent } from "./terms.component";

@NgModule({
    declarations: [
        TermsComponent
    ],
    imports: [
        TermsRoutingModule,
        CommonModule
    ],
    providers: []
})
export class TermsModule {}
