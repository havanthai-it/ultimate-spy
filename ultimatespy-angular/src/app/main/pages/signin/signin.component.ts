import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SocialAuthService, GoogleLoginProvider } from 'angularx-social-login';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SigninComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private authService: SocialAuthService
    ) { }

  username: string;
  password: string;
  redirect: string;
  errorMessage: string;
  loading: boolean = false;

  signinForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  get f() {
    return this.signinForm.controls;
  }

  ngOnInit(): void {
    // reset login status
    // this.jwtService.signout();

    // get return url from route parameters or default to '/'
    this.redirect = this.route.snapshot.queryParams['redirect'] || '/';
  }

  signin(): void {
    if (!this.username || !this.password) {
      this.errorMessage = "Please fill in all the information";
      return;
    }
    this.loading = true;
    this.userService.authenticate(this.username, btoa(this.password)).subscribe(
      data => {
        const userInfo = {
          id: data.user.id,
          email: data.user.email,
          fullName: data.user.fullName,
          status: data.user.status
        };
        localStorage.setItem('token', data.token);
        localStorage.setItem('user', JSON.stringify(userInfo));
        this.loading = false;
        this.router.navigateByUrl(this.redirect);
      },
      error => {
        console.log(error);
        if (error.status === 401) {
          this.errorMessage = 'Email or password is incorrect!'
        } else {
          this.errorMessage = error.error.message ? error.error.message : 'Sorry, an error occurred while processing your request';
        }
        this.loading = false;
      }
    );
  }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID)
      .then(user => {
        console.log(user);
      })
      .catch(error => {
        console.log(error);
      });
  }

  signOut(): void {
    this.authService.signOut();
  }

}
