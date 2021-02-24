import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SocialAuthService, GoogleLoginProvider } from 'angularx-social-login';
import { UserService } from '../../../core/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SignupComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private authService: SocialAuthService
    ) { }

  redirect: string;
  errorMessage: string;
  loading: boolean = false;
  confirmPasswordMatched: boolean = false;
  signupComplete = false;

  signupForm = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
    confirmPassword: new FormControl('', [Validators.required])
  });

  user: any = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: ''
  }

  get f() {
    return this.signupForm.controls;
  }

  ngOnInit(): void {
    // get return url from route parameters or default to '/'
    this.redirect = this.route.snapshot.queryParams['redirect'] || '/';
  }

  signup(): void {
    if (!this.user.firstName || !this.user.lastName || !this.user.email || !this.user.password || !this.user.confirmPassword) {
      this.errorMessage = 'Please fill in all the information';
      return;
    }
    if (this.user.password !== this.user.confirmPassword) {
      this.errorMessage = 'Confirm password does not match';
      return;
    }
    this.loading = true;
    let requestData = {
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email,
      password: btoa(this.user.password)
    }
    this.userService.signup(requestData).subscribe(
      data => {
        console.log(data);
        this.loading = false;
        this.signupComplete = true;
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message ? error.error.message : 'Sorry, an error occurred while processing your request';
        this.loading = false;
      }
    );
  }

  signUpWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID)
      .then(user => {
        console.log(user);
        this.userService.signinGoogle(user.idToken).subscribe(
          data => {
            const userInfo = {
              id: data.user.id,
              email: data.user.email,
              firstName: data.user.firstName,
              lastName: data.user.lastName,
              fullName: data.user.firstName + ' ' + data.user.lastName,
              status: data.user.status,
              createDate: data.user.createDate,
              plan: data.user.plan
            };
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify(userInfo));
            window.location.href = this.redirect;
          },
          error => {
            console.log(error);
            this.errorMessage = error.error.message ? error.error.message : 'Sorry, an error occurred while processing your request';
          }
        );
      })
      .catch(error => {
        console.log(error);
      });
  }

  onChangeConfirmPassword(): void {
    if (this.user.confirmPassword === this.user.password) {
      this.confirmPasswordMatched = true;
      this.signupForm.controls['confirmPassword'].setErrors(null);
    } else {
      this.confirmPasswordMatched = false;
      this.signupForm.controls['confirmPassword'].setErrors([{'CONFIRM_PASSWORD_NOT_MATCHED': ''}])
    }
  }

}
