import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { SocialLoginModule, GoogleLoginProvider, SocialAuthServiceConfig } from 'angularx-social-login';
import { MainComponent } from './main/main.component';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { ToastrModule } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthInterceptor } from 'src/app/core/interceptors/AuthInterceptor';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    CommonModule,
    SharedModule,
    CoreModule,
    SocialLoginModule,
    ToastrModule.forRoot({
      timeOut: 1000,
      positionClass: 'toast-bottom-right'
    })
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useFactory: function (router: Router) {
        return new AuthInterceptor(router);
      },
      multi: true,
      deps: [Router]
    },
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
            provider: new GoogleLoginProvider('850222883858-gmug4hk3jnpc7v0tjtvgjiheq4i69fha.apps.googleusercontent.com'),
          }
        ],
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
