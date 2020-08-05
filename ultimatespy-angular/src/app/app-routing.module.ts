import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainRoutingModule } from './main/main-routing.module';
import { AppComponent } from './app.component';

const routes: Routes = [
  { path: '', loadChildren: () =>  MainRoutingModule}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
