import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { JwtService } from './services/jwt.service';
import { AuthGuard } from './guards/AuthGuard';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule 
  ],
  providers: [
    AuthGuard,
    JwtService
  ]
})
export class CoreModule { }
