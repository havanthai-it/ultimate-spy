import { Component, OnInit } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  pathName: string = '';

  constructor(private titleService: Title, private metaService: Meta) {
    this.titleService.setTitle('AdsCrawlr | Profile');
    this.metaService.addTags([
      { name: 'keywords', content: 'AdsCrawlr, Ads Spy, Ads Spy Tool, Ads Crawl, Ads Crawl Tool, Facebook Ads Spy, Facebook Ads Crawl' },
      { name: 'description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
      { name: 'robots', content: 'index, follow' },
      { name: 'og:title', content: 'AdsCrawlr | Profile'},
      { name: 'og:image', content: 'https://adscrawlr.com/assets/img/home-cover.png' },
      { name: 'og:description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
      { name: 'og:url', content: 'https://adscrawlr.com/' }
    ]);
  }

  ngOnInit(): void {
    this.pathName = window.location.pathname;
  }

  isActive(path) {
    return this.pathName.startsWith(path);
  }

}
