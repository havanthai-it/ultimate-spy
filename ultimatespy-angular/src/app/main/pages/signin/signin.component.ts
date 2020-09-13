import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { FormGroup, FormControl } from '@angular/forms';
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
  returnUrl: string;
  errorMessage: string;
  loading: boolean = false;

  signinForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  ngOnInit(): void {
    // reset login status
    // this.jwtService.signout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  signin(): void {
    if (!this.username || !this.password) {
      return;
    }
    this.loading = true;
    this.userService.authenticate(this.username, btoa(this.password)).subscribe(
      data => {
        console.log(data);
        localStorage.setItem('jwtToken', data.jwtToken);
        this.loading = false;
        this.router.navigateByUrl(this.returnUrl);
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
