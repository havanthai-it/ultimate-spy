import { Component, OnInit, ViewChild } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';
import { SpySearchComponent } from './spy-search/spy-search.component';

declare var $: any;

@Component({
  selector: 'app-spy',
  templateUrl: './spy.component.html',
  styleUrls: ['./spy.component.scss']
})
export class SpyComponent implements OnInit {
  @ViewChild('spySearch') spySearchComponent: SpySearchComponent;

  searchResult: any = {};
  isSearching: boolean = false;

  user: any;
  token: string;

  constructor(private titleService: Title, private metaService: Meta) {
    this.titleService.setTitle('AdsCrawlr | Ads Spy');
    this.metaService.addTags([
      { name: 'keywords', content: 'AdsCrawlr, Ads Spy, Ads Spy Tool, Ads Crawl, Ads Crawl Tool, Facebook Ads Spy, Facebook Ads Crawl' },
      { name: 'description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
      { name: 'robots', content: 'index, follow' },
      { name: 'og:title', content: 'AdsCrawlr | Ads Spy'},
      { name: 'og:image', content: 'https://adscrawlr.com/assets/img/home-cover.png' },
      { name: 'og:description', content: 'AdsCrawlr is the #1 Ads Spy Tool for POD, Shopify & Woocommerce sellers. Discover new ideas or niches tracked by thousands of users. Daily trending ads picked by big data & our intelligence.' },
      { name: 'og:url', content: 'https://adscrawlr.com/' }
    ]);
  }

  ngOnInit(): void {
    $('[data-toggle="tooltip"]').tooltip();
    
    this.user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {};
    this.token = localStorage.getItem('token');
  }
  
  setSearchResult(searchResult: any): void {
    this.searchResult = searchResult;
  }

  onSearching(isSearching: boolean): void {
    this.isSearching = isSearching;
  }

  search(page: number): void {
    if (!this.user || !this.token) return;
    this.spySearchComponent.search(page + 1);
  }

  /**
   * i from 0 -> total - 1
   */
  showPage(i: number): boolean {
    if (this.searchResult.pages <= 8) return true;
    if (i === 0 || i === (this.searchResult.pages -  1)) return true;
    if ((i + 1) > (this.searchResult.page - 1) && (i + 1) < (this.searchResult.page + 3)) return true;
    if (this.searchResult.page === 0 && (i + 1) < 6) return true;
    if (this.searchResult.page === (this.searchResult.pages -  1) && (i + 1) > (this.searchResult.pages -  3)) return true;
    return false;
  }

  showPageEllipsis(i: number): boolean {
    if (this.searchResult.pages > 8 && this.searchResult.page === 0 && (i + 1) < 6) return false;
    if (this.searchResult.pages > 8 && this.searchResult.page === 0 && (i + 1) === 6) return true;
    if (this.searchResult.pages > 8 && this.searchResult.page === (this.searchResult.pages -  1) && (i + 1) > (this.searchResult.pages -  3)) return false;
    if (this.searchResult.pages > 8 && this.searchResult.page === (this.searchResult.pages -  1) && (i + 1) === (this.searchResult.pages -  3)) return true;
    if (this.searchResult.pages > 8 && i > 0 && i < this.searchResult.pages - 1 && ((i + 1) === this.searchResult.page - 1 || (i + 1) === this.searchResult.page + 3)) return true;
    return false;
  }

  min (a: number, b: number) {
    return Math.min(a, b);
  }

  max (a: number, b: number) {
    return Math.max(a, b);
  }

}
