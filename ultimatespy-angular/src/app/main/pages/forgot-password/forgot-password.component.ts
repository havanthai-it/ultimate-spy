import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Meta, Title } from '@angular/platform-browser';
import { UserService } from '../../../core/services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ForgotPasswordComponent implements OnInit {

  constructor(
    private titleService: Title,
    private metaService: Meta,
    private userService: UserService
    ) {
      this.titleService.setTitle('AdsCrawlr | Forgot password');
      this.metaService.addTags([
        { name: 'keywords', content: 'AdsCrawlr, Ads Spy, Ads Spy Tool, Ads Crawl, Ads Crawl Tool, Facebook Ads Spy, Facebook Ads Crawl' },
        { name: 'description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
        { name: 'robots', content: 'index, follow' },
        { name: 'og:title', content: 'AdsCrawlr | Forgot password'},
        { name: 'og:image', content: 'https://adscrawlr.com/assets/img/home-cover.png' },
        { name: 'og:description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
        { name: 'og:url', content: 'https://adscrawlr.com/' }
      ]);
    }

  email: string;
  errorMessage: string;
  loading: boolean = false;
  requestComplete: boolean = false;

  forgotPasswordForm = new FormGroup({
    email: new FormControl('', [Validators.required])
  });

  get f() {
    return this.forgotPasswordForm.controls;
  }

  ngOnInit(): void {
  }

  forgotPassword(): void {
    if (!this.email) {
      this.errorMessage = 'Please fill in all the information';
      return;
    }
    this.loading = true;
    this.userService.forgotPassword(this.email).subscribe(
      data => {
        this.loading = false;
        this.requestComplete = true;
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message ? error.error.message : 'Sorry, an error occurred while processing your request';
        this.loading = false;
      }
    );
  }

}
