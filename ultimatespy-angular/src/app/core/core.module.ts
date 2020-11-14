import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from './services/user.service';
import { AuthGuard } from './guards/AuthGuard';
import { PostService } from './services/post.service';
import { UserPostService } from './services/user-post.service';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule 
  ],
  providers: [
    AuthGuard,
    UserService,
    PostService,
    UserPostService
  ]
})
export class CoreModule { }
