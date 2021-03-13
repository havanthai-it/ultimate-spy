import { Component, OnInit } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';

@Component({
  selector: 'app-terms',
  templateUrl: './terms.component.html',
  styleUrls: ['./terms.component.scss']
})
export class TermsComponent implements OnInit {

  constructor(private titleService: Title, private metaService: Meta) {
    this.titleService.setTitle('AdsCrawlr | Terms of Services');
    this.metaService.addTags([
      { name: 'keywords', content: 'AdsCrawlr, Ads Spy, Ads Spy Tool, Ads Crawl, Ads Crawl Tool, Facebook Ads Spy, Facebook Ads Crawl' },
      { name: 'description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
      { name: 'robots', content: 'index, follow' },
      { name: 'og:title', content: 'AdsCrawlr | Terms of Services'},
      { name: 'og:image', content: 'https://adscrawlr.com/assets/img/home-cover.png' },
      { name: 'og:description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
      { name: 'og:url', content: 'https://adscrawlr.com/' }
    ]);
  }

  ngOnInit(): void {
  }

}
