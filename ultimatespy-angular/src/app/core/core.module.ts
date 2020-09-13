import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from './services/user.service';
import { AuthGuard } from './guards/AuthGuard';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule 
  ],
  providers: [
    AuthGuard,
    UserService
  ]
})
export class CoreModule { }
