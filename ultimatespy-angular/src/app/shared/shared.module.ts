import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoadingComponent } from './components/loading/loading.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { PlanCtaDialogComponent } from './components/plan-cta-dialog/plan-cta-dialog.component';
import { MatIconModule } from '@angular/material/icon';
import { ErrorDialogComponent } from './components/error-dialog/error-dialog.component';
import { RanoutDialogComponent } from './components/ranout-dialog/ranout-dialog.component';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    LoadingComponent,
    ConfirmDialogComponent,
    PlanCtaDialogComponent,
    ErrorDialogComponent,
    RanoutDialogComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatIconModule
  ],
  exports: [
    FlexLayoutModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    HeaderComponent,
    FooterComponent,
    LoadingComponent,
    ConfirmDialogComponent,
    PlanCtaDialogComponent
  ]
})
export class SharedModule { }
