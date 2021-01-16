import { Component, OnInit, ViewEncapsulation, Output, EventEmitter } from '@angular/core';
import { PostService } from '../../../../core/services/post.service';
import { FacebookPostQuery } from '../../../../core/models/FacebookPostQuery';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { ActivatedRoute, Params } from '@angular/router';
import { UserPostService } from 'src/app/core/services/user-post.service';

@Component({
  selector: 'app-spy-search',
  templateUrl: './spy-search.component.html',
  styleUrls: ['./spy-search.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpySearchComponent implements OnInit {
  @Output() searchResult = new EventEmitter<any>();
  @Output() listSavedIds = new EventEmitter<string[]>();
  @Output() listTrackedIds = new EventEmitter<string[]>();
  @Output() onSearching = new EventEmitter<boolean>();
  
  constructor(
    private activatedRoute: ActivatedRoute,
    private postService: PostService,
    private userPostService : UserPostService) {
  }

  userId: string;
  searchQuery: string;
  total: number = 0;
  isSearching: boolean = false;

  likeRange: any = {
    from: 0,
    to: 10000,
    options: {
      floor: 0,
      ceil: 10000,
      //step: 10,
      disabled: false,
      hideLimitLabels: true,
      hidePointerLabels: true
    }
  };

  commentRange: any = {
    from: 0,
    to: 10000,
    options: {
      floor: 0,
      ceil: 10000,
      //step: 10,
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
    { label: 'Clothes', value: 'Clothes' },
    { label: 'Technology', value: 'Technology' }
  ];
  lstTypes: any[] = [
    { label: 'Image', value: 'IMAGE' },
    { label: 'Video', value: 'VIDEO' }
  ];
  lstPlatforms: any[] = [
    { label: 'POD', value: 'pod' },
    { label: 'Shopify', value: 'shopify' },
    { label: 'Woocommerce', value: 'woocommerce' },
    { label: 'Amazon', value: 'amazon' },
    { label: 'Ebay', value: 'ebay' },
    { label: 'Etsy', value: 'etsy' },
    { label: 'Redbubble', value: 'redbubble' },
    { label: 'Teespring', value: 'teespring' },
    { label: 'Bigcommerce', value: 'bigcommerce' },
    { label: 'Magento', value: 'magento' },
    { label: 'Merchking', value: 'merchking' },
    { label: 'Gearbubble', value: 'gearbubble' },
    { label: 'Spreadshirt', value: 'spreadshirt' },
    { label: 'Sunfrog', value: 'sunfrog' },
    { label: 'Teechip', value: 'teechip' },
    { label: 'Teehag', value: 'teehag' },
    { label: 'Teepublic', value: 'teepublic' },
    { label: 'Teezily', value: 'teezily' },
    { label: 'Shopbase', value: 'shopbase' },
    { label: 'Teemill', value: 'teemill' },
    { label: 'Gearlaunch', value: 'gearlaunch' },
    { label: 'Merchize', value: 'merchize' },
    { label: 'Hostingrocket', value: 'hostingrocket' }
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
    let user = localStorage.getItem('user');
    this.userId = user ? JSON.parse(user).id : '';

    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.query.keyword = params.keyword ? params.keyword : '';
      this.query.page = params.page ? parseInt(params.page) -1 : 0;
      this.fromDate.setDate(this.now.getDate() - 365);
    });
  }

  ngAfterViewInit(): void {
    this.search(0, false);
  }

  search(page: number, scrollToTop: boolean): void {
    if (this.isSearching) return;
    if (scrollToTop)  window.scroll(0,0);
    this.onSearching.emit(true);
    this.isSearching = true;
    this.query.fromDate = this.fromDate.toISOString().split('T')[0];
    this.query.toDate = this.toDate.toISOString().split('T')[0];
    this.query.page = page;
    this.query.minLikes = this.likeRange.from === this.likeRange.options.floor ? '' : this.likeRange.from;
    this.query.maxLikes = this.likeRange.to === this.likeRange.options.ceil ? '' : this.likeRange.to;
    this.query.minComments = this.commentRange.from === this.commentRange.options.floor ? '' : this.commentRange.from;
    this.query.maxComments = this.commentRange.to === this.commentRange.options.ceil ? '' : this.commentRange.to;
    this.postService.searchFacebookPost(this.query).subscribe(
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

    this.loadSavedPostIds(this.userId);
    this.loadTrackedPostIds(this.userId);
  }

  loadSavedPostIds(userId: string): void {
    if (userId) {
      this.userPostService.getListFacebookPostId(userId, "saved").subscribe(
        data => {
          this.listSavedIds.emit(data);
        },
        error => {
          console.log(error);
        }
      );
    }
  }

  loadTrackedPostIds(userId: string): void {
    if (userId) {
      this.userPostService.getListFacebookPostId(userId, "tracked").subscribe(
        data => {
          this.listTrackedIds.emit(data);
        },
        error => {
          console.log(error);
        }
      );
    }
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
