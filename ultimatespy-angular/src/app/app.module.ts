import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { SocialLoginModule, GoogleLoginProvider, SocialAuthServiceConfig } from 'angularx-social-login';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MainComponent } from './main/main.component';
import { HomeComponent } from './main/pages/home/home.component';
import { SigninComponent } from './main/pages/signin/signin.component';
import { SignupComponent } from './main/pages/signup/signup.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ForgotPasswordComponent } from './main/pages/forgot-password/forgot-password.component';
import { SpyComponent } from './main/pages/spy/spy.component';
import { SpySearchComponent } from './main/pages/spy/spy-search/spy-search.component';
import { SpySearchResultComponent } from './main/pages/spy/spy-search-result/spy-search-result.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { SpySearchResultItemComponent } from './main/pages/spy/spy-search-result/spy-search-result-item/spy-search-result-item.component';
import { SpySearchResultItemDialogComponent } from './main/pages/spy/spy-search-result/spy-search-result-item/spy-search-result-item-dialog/spy-search-result-item-dialog.component';
import { CheckoutComponent } from './main/pages/checkout/checkout.component';
import { DashboardComponent } from './main/pages/dashboard/dashboard.component';
import { DashboardProfileComponent } from './main/pages/dashboard/dashboard-profile/dashboard-profile.component';
import { DashboardPlanComponent } from './main/pages/dashboard/dashboard-plan/dashboard-plan.component';
import { Ng5SliderModule } from 'ng5-slider';
import { NgxPayPalModule } from 'ngx-paypal';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
//import { PrivacyComponent } from './main/pages/privacy/privacy.component';
import { TermsComponent } from './main/pages/terms/terms.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    HomeComponent,
    SigninComponent,
    SignupComponent,
    ForgotPasswordComponent,
    SpyComponent,
    SpySearchComponent,
    SpySearchResultComponent,
    SpySearchResultItemComponent,
    SpySearchResultItemDialogComponent,
    CheckoutComponent,
    DashboardComponent,
    DashboardProfileComponent,
    DashboardPlanComponent,
    ConfirmDialogComponent,
    //PrivacyComponent,
    TermsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    CommonModule,
    SharedModule,
    CoreModule,
    NgxPayPalModule,
    SocialLoginModule,
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
  providers: [
    { 
      provide: MAT_DATE_LOCALE,
      useValue: 'en-GB'
    },
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider('clientId'),
          }
        ],
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
