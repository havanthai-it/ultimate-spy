import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SocialAuthService, GoogleLoginProvider } from 'angularx-social-login';
import { Meta, Title } from '@angular/platform-browser';

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
    private titleService: Title,
    private metaService: Meta,
    private userService: UserService,
    private authService: SocialAuthService
    ) {
      this.titleService.setTitle('AdsCrawlr | Sign in');
      this.metaService.addTags([
        { name: 'keywords', content: 'AdsCrawlr, Ads Spy, Ads Spy Tool, Ads Crawl, Ads Crawl Tool, Facebook Ads Spy, Facebook Ads Crawl' },
        { name: 'description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
        { name: 'robots', content: 'index, follow' },
        { name: 'og:title', content: 'AdsCrawlr | Sign in'},
        { name: 'og:image', content: 'https://adscrawlr.com/assets/img/home-cover.png' },
        { name: 'og:description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
        { name: 'og:url', content: 'https://adscrawlr.com/' }
      ]);
    }

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
    this.userService.signin(this.username, btoa(this.password)).subscribe(
      data => {
        const userInfo = {
          id: data.user.id,
          email: data.user.email,
          firstName: data.user.firstName,
          lastName: data.user.lastName,
          fullName: data.user.firstName + ' ' + data.user.lastName,
          role: data.user.role,
          status: data.user.status,
          createDate: data.user.createDate,
          plan: data.user.plan,
          code: data.user.code,
          paypalName: data.user.paypalName,
          paypalAccount: data.user.paypalAccount
        };
        localStorage.setItem('token', data.token);
        localStorage.setItem('user', JSON.stringify(userInfo));
        this.loading = false;
        window.location.href = this.redirect;
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
        this.userService.signinGoogle(user.idToken).subscribe(
          data => {
            const userInfo = {
              id: data.user.id,
              email: data.user.email,
              firstName: data.user.firstName,
              lastName: data.user.lastName,
              fullName: data.user.firstName + ' ' + data.user.lastName,
              role: data.user.role,
              status: data.user.status,
              createDate: data.user.createDate,
              plan: data.user.plan,
              code: data.user.code,
              paypalName: data.user.paypalName,
              paypalAccount: data.user.paypalAccount
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

  signOut(): void {
    this.authService.signOut();
  }

}
