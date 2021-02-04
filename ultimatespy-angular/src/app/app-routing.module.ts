import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SigninComponent } from './main/pages/signin/signin.component';
import { MainComponent } from './main/main.component';
import { HomeComponent } from './main/pages/home/home.component';
import { AuthGuard } from './core/guards/AuthGuard';
import { SignupComponent } from './main/pages/signup/signup.component';
import { ForgotPasswordComponent } from './main/pages/forgot-password/forgot-password.component';
import { SpyComponent } from './main/pages/spy/spy.component';
import { CheckoutComponent } from './main/pages/checkout/checkout.component';
import { DashboardComponent } from './main/pages/dashboard/dashboard.component';
import { DashboardProfileComponent } from './main/pages/dashboard/dashboard-profile/dashboard-profile.component';
import { DashboardPlanComponent } from './main/pages/dashboard/dashboard-plan/dashboard-plan.component';
// import { PrivacyComponent } from './main/pages/privacy/privacy.component';
import { TermsComponent } from './main/pages/terms/terms.component';

const routes: Routes = [
  {
    path: 'signin',
    component: SigninComponent
  },
  {
    path: 'signup',
    component: SignupComponent
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent
  },
  {
    path: '',
    component: MainComponent,
    children: [
      { path: '', component: HomeComponent },
      { path: 'home', redirectTo: '', pathMatch: 'full' },
      { path: 'ads', component: SpyComponent, pathMatch: 'full' },
      { path: 'privacy', loadChildren: () => import('./main/pages/privacy/privacy.module').then(m => m.PrivacyModule) },
      { path: 'terms', component: TermsComponent, pathMatch: 'full' },
      { path: 'checkout', component: CheckoutComponent, pathMatch: 'full' },
      {
        path: 'dashboard',
        component: DashboardComponent,
        children: [
          { path: 'profile', component: DashboardProfileComponent, pathMatch: 'full', canActivate: [AuthGuard] },
          { path: 'plan', component: DashboardPlanComponent, pathMatch: 'full', canActivate: [AuthGuard] }
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
