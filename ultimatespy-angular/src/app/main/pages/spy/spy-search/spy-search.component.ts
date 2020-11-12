import { Component, OnInit, ViewEncapsulation, Output, EventEmitter } from '@angular/core';
import { SpyService } from '../../../../core/services/spy.service';
import { FacebookPostQuery } from '../../../../core/models/FacebookPostQuery';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-spy-search',
  templateUrl: './spy-search.component.html',
  styleUrls: ['./spy-search.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpySearchComponent implements OnInit {
  @Output() searchResult = new EventEmitter<any>();
  @Output() onSearching = new EventEmitter<boolean>();
  
  constructor(private activatedRoute: ActivatedRoute, private spyService: SpyService) {
  }

  searchQuery: string;
  total: number = 0;
  isSearching: boolean = false;

  likeRange: any = {
    from: 0,
    to: 100000,
    options: {
      floor: 0,
      ceil: 100000,
      // step: 100,
      disabled: false,
      hideLimitLabels: true,
      hidePointerLabels: true
    }
  };

  commentRange: any = {
    from: 0,
    to: 100000,
    options: {
      floor: 0,
      ceil: 100000,
      // step: 100,
      disabled: false,
      hideLimitLabels: true,
      hidePointerLabels: true
    }
  }

  query: FacebookPostQuery = {
    fromDate: '',
    toDate: '',
    page: 0,
    pageSize: 24,
    keyword: '',
    pixelId: '',
    facebookPageUsername: '',
    category: '',
    type: '',
    country: '',
    language: '',
    website: '',
    platform: '',
    minLikes: '',
    maxLikes: '',
    minComments: '',
    maxComments: '',
    sort: 'date'
  }

  lstCategories: any[] = [
    { label: 'All', value: '' },
    { label: 'Clothes', value: 'Clothes' },
    { label: 'Technology', value: 'Technology' }
  ];
  lstTypes: any[] = [
    { label: 'All', value: '' },
    { label: 'Image', value: 'Image' },
    { label: 'Video', value: 'Video' }
  ];
  lstCountries: any[] = [
    { label: 'All', value: '' },
    { label: 'England', value: 'EN' },
    { label: 'Vietnam', value: 'VN' }
  ];
  lstPlatforms: any[] = [
    { label: 'All', value: '' },
    { label: 'Anazon', value: 'EN' },
    { label: 'Ebay', value: 'VN' }
  ];
  lstSorts: any[] = [
    { label: 'Date', value: 'date' },
    { label: 'Like', value: 'like' },
    { label: 'comment', value: 'comment' }
  ];

  
  now: Date = new Date();
  fromDate: Date = new Date();
  toDate: Date = new Date();

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.query.page = params.page ? parseInt(params.page) -1 : 0;
      this.fromDate.setDate(this.now.getDate() - 365);
      this.search(0, false);
    });
  }

  search(page: number, scrollToTop: boolean): void {
    if (this.isSearching) return;
    if (scrollToTop)  window.scroll(0,0);
    this.onSearching.emit(true);
    this.isSearching = true;
    // this.searchResult.emit({ list: [], total: 0 });
    
    this.query.page = page;
    this.query.minLikes = this.likeRange.from === this.likeRange.options.floor ? '' : this.likeRange.from;
    this.query.maxLikes = this.likeRange.to === this.likeRange.options.ceil ? '' : this.likeRange.to;
    this.query.minComments = this.commentRange.from === this.commentRange.options.floor ? '' : this.commentRange.from;
    this.query.maxComments = this.commentRange.to === this.commentRange.options.ceil ? '' : this.commentRange.to;
    this.spyService.searchFacebookPost(this.query).subscribe(
      data => {
        this.total = data.total;
        data.page = this.query.page;
        data.pageSize = this.query.pageSize;
        data.pages = Math.ceil(data.total / data.pageSize)
        this.searchResult.emit(data);
        this.onSearching.emit(false);
        this.isSearching = false;
      },
      error => {
        console.log(error);
        this.onSearching.emit(false);
        this.isSearching = false;
      }
    )
  }

  saveSearchQuery(): void {
    
  }

  numericOnly(event): boolean {    
    let pattern = /^([0-9])$/;
    let result = pattern.test(event.key);
    return result;
  }

  onLikeToChange(event): void {
    let value = event.target.value ? parseInt(event.target.value.replace(/\D/g,'')) : 0
    this.likeRange.to = value;
  }

  onCommentToChange(event): void {
    let value = event.target.value ? parseInt(event.target.value.replace(/\D/g,'')) : 0
    this.commentRange.to = value;
  }

}
