import { Component, OnInit } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss']
})
export class ConfirmComponent implements OnInit {

  confirmId: string;
  userId: string;

  constructor(private route: ActivatedRoute,
    private titleService: Title,
    private metaService: Meta,
    private userService: UserService) {
      this.titleService.setTitle('AdsCrawlr | Confirm');
      this.metaService.addTags([
        { name: 'keywords', content: 'AdsCrawlr, Ads Spy, Ads Spy Tool, Ads Crawl, Ads Crawl Tool, Facebook Ads Spy, Facebook Ads Crawl' },
        { name: 'description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
        { name: 'robots', content: 'index, follow' },
        { name: 'og:title', content: 'AdsCrawlr | Confirm'},
        { name: 'og:image', content: 'https://adscrawlr.com/assets/img/home-cover.png' },
        { name: 'og:description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
        { name: 'og:url', content: 'https://adscrawlr.com/' }
      ]);
    }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.confirmId = params.confirmId;
      this.userId = atob(params.confirmId);
      this.userService.confirm(this.userId, this.confirmId).subscribe(
        data => {
          alert('Your email has been confirmed!');
          window.location.href = '/signin';
        },
        error => {
          console.log(error);
        }
      );
    });
  }

}
