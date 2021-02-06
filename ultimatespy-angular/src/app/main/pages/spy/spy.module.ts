import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonToggleModule } from "@angular/material/button-toggle";
import { MatNativeDateModule } from "@angular/material/core";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { NgxChartsModule } from "@swimlane/ngx-charts";
import { Ng5SliderModule } from "ng5-slider";
import { SpyRoutingModule } from "./spy-routing.module";
import { SpySearchResultItemDialogComponent } from "./spy-search-result/spy-search-result-item/spy-search-result-item-dialog/spy-search-result-item-dialog.component";
import { SpySearchResultItemComponent } from "./spy-search-result/spy-search-result-item/spy-search-result-item.component";
import { SpySearchResultComponent } from "./spy-search-result/spy-search-result.component";
import { SpySearchComponent } from "./spy-search/spy-search.component";
import { SpyComponent } from "./spy.component";

@NgModule({
    declarations: [
        SpyComponent,
        SpySearchComponent,
        SpySearchResultComponent,
        SpySearchResultItemComponent,
        SpySearchResultItemDialogComponent,
    ],
    imports: [
        SpyRoutingModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        Ng5SliderModule,
        NgxChartsModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatProgressSpinnerModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatButtonToggleModule

    ],
    providers: []
})
export class SpyModule {}
