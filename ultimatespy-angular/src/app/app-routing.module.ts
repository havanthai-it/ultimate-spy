import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './main/main.component';
import { AuthGuard } from './core/guards/AuthGuard';

const routes: Routes = [
  {
    path: 'signin',
    loadChildren: () => import('./main/pages/signin/signin.module').then(m => m.SigninModule)
  },
  {
    path: 'signup',
    loadChildren: () => import('./main/pages/signup/signup.module').then(m => m.SignupModule)
  },
  {
    path: 'forgot-password',
    loadChildren: () => import('./main/pages/forgot-password/forgot-password.module').then(m => m.ForgotPasswordModule)
  },
  {
    path: 'reset-password',
    loadChildren: () => import('./main/pages/reset-password/reset-password.module').then(m => m.ResetPasswordModule)
  },
  {
    path: '',
    component: MainComponent,
    children: [
      { path: '', loadChildren: () => import('./main/pages/home/home.module').then(m => m.HomeModule) },
      { path: 'home', redirectTo: '', pathMatch: 'full' },
      { path: 'ads', loadChildren: () => import('./main/pages/spy/spy.module').then(m => m.SpyModule) },
      { path: 'faq', loadChildren: () => import('./main/pages/faq/faq.module').then(m => m.FaqModule) },
      { path: 'about', loadChildren: () => import('./main/pages/about/about.module').then(m => m.AboutModule) },
      { path: 'privacy', loadChildren: () => import('./main/pages/privacy/privacy.module').then(m => m.PrivacyModule) },
      { path: 'terms', loadChildren: () => import('./main/pages/terms/terms.module').then(m => m.TermsModule) },
      { path: 'checkout', loadChildren: () => import('./main/pages/checkout/checkout.module').then(m => m.CheckoutModule) },
      { path: 'dashboard', loadChildren: () => import('./main/pages/dashboard/dashboard.module').then(m => m.DashboardModule) },
      { path: 'confirm', loadChildren: () => import('./main/pages/confirm/confirm.module').then(m => m.ConfirmModule) },
      { path: 'referral', loadChildren: () => import('./main/pages/referral/referral.module').then(m => m.ReferralModule) }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
