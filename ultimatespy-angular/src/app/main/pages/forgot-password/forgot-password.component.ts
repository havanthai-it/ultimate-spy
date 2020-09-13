import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SocialAuthService, GoogleLoginProvider } from 'angularx-social-login';
import { UserService } from '../../../core/services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ForgotPasswordComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private authService: SocialAuthService
    ) { }

  email: string;
  errorMessage: string;
  loading: boolean = false;

  forgotPasswordForm = new FormGroup({
    email: new FormControl('')
  });


  ngOnInit(): void {
  }

  forgotPassword(): void {
    if (!this.email) {
      return;
    }
    this.loading = true;
    this.userService.resetPassword(this.email).subscribe(
      data => {
        console.log(data);
        this.loading = false;
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message ? error.error.message : 'Sorry, an error occurred while processing your request';
        this.loading = false;
      }
    );
  }

}
