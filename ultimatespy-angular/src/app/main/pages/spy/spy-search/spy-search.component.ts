import { Component, OnInit, ViewEncapsulation, Output, EventEmitter } from '@angular/core';
import { PostService } from '../../../../core/services/post.service';
import { FacebookPostQuery } from '../../../../core/models/FacebookPostQuery';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { ActivatedRoute, Params } from '@angular/router';
import { UserPostService } from 'src/app/core/services/user-post.service';
import { MatDialog } from '@angular/material/dialog';
import { RanoutDialogComponent } from 'src/app/shared/components/ranout-dialog/ranout-dialog.component';

@Component({
  selector: 'app-spy-search',
  templateUrl: './spy-search.component.html',
  styleUrls: ['./spy-search.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpySearchComponent implements OnInit {
  @Output() searchResult = new EventEmitter<any>();
  @Output() onSearching = new EventEmitter<boolean>();
  
  constructor(
    private dialog: MatDialog,
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
    filter: 'all',
    sort: 'newest'
  }

  lstCategories: any[] = [
    { label: 'Artist', value: 'Artist' },
    { label: 'Athlete', value: 'Athlete' },
    { label: 'Clothing (brand)', value: 'Clothing (brand)' },
    { label: 'Community', value: 'Community' },
    { label: 'Company', value: 'Company' },
    { label: 'Entertainment website', value: 'Entertainment website' },
    { label: 'Film', value: 'Film' },
    { label: 'Health/beauty', value: 'Health/beauty' },
    { label: 'Interest', value: 'Interest' },
    { label: 'Jewellery/watches', value: 'Jewellery/watches' },
    { label: 'Just for fun', value: 'Just for fun' },
    { label: 'Local business', value: 'Local business' },
    { label: 'Magazine', value: 'Magazine' },
    { label: 'Media/news company', value: 'Media/news company' },
    { label: 'Musician/band', value: 'Musician/band' },
    { label: 'News and media website', value: 'News and media website' },
    { label: 'Non-profit organisation', value: 'Non-profit organisation' },
    { label: 'Personal blog', value: 'Personal blog' },
    { label: 'Product/service', value: 'Product/service' },
    { label: 'Public Figure', value: 'Public Figure' },
    { label: 'Retail company', value: 'Retail company' },
    { label: 'Shopping & retail', value: 'Shopping & retail' },
    { label: 'TV programme', value: 'TV programme' },
    { label: 'Website', value: 'Website' }
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
    { label: 'Newest', value: 'newest' },
    { label: 'Like', value: 'like' },
    { label: 'comment', value: 'comment' },
    { label: 'Share', value: 'share' }
  ];

  
  now: Date = new Date();
  fromDate: Date;
  toDate: Date;

  ngOnInit(): void {
    let user = localStorage.getItem('user');
    this.userId = user ? JSON.parse(user).id : '';

    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.query.keyword = params.keyword ? params.keyword : '';
      this.query.category = params.category ? params.category.split(',') : '';
      this.query.type = params.type ? params.type.split(',') : '';
      this.query.platform = params.platform ? params.platform.split(',') : '';
      this.query.fromDate = params.fromDate ? params.fromDate : '';
      this.query.toDate = params.toDate ? params.toDate : '';
      this.query.minLikes = params.minLikes ? parseInt(params.minLikes) : this.likeRange.options.floor;
      this.query.maxLikes = params.maxLikes ? parseInt(params.maxLikes) : this.likeRange.options.ceil;
      this.query.minComments = params.minComments ? parseInt(params.minComments) : this.commentRange.options.floor;
      this.query.maxComments = params.maxComments ? parseInt(params.maxComments) : this.commentRange.options.ceil;
      this.query.filter = params.filter ? params.filter : 'all';
      this.query.sort = params.sort ? params.sort : 'newest';
      this.query.page = params.page ? parseInt(params.page) - 1 : 0;

      this.likeRange.from = this.query.minLikes;
      this.likeRange.to = this.query.maxLikes;
      this.commentRange.from = this.query.minComments;
      this.commentRange.to = this.query.maxComments;
      this.toDate = params.toDate ? new Date(params.toDate) : undefined;
      this.fromDate = params.fromDate ? new Date(params.fromDate) : undefined;
      //this.toDate.setDate(this.now.getDate());
      //this.fromDate.setDate(this.now.getDate() - 365);

    });
  }

  ngAfterViewInit(): void {
    this.postService.searchFacebookPost(this.query).subscribe(
      data => {
        this.total = data.total;
        data.page = this.query.page;
        data.pageSize = this.query.pageSize;
        data.pages = Math.min(Math.ceil(data.total / data.pageSize), 100)
        this.searchResult.emit(data);
        this.onSearching.emit(false);
        this.isSearching = false;
      },
      error => {
        console.log(error);
        this.onSearching.emit(false);
        this.isSearching = false;
        if (error && error.status && error.status === 401) {
          window.location.href = '/signin';
        } else if (error && error.status && error.status === 403) {
          const dialogRef = this.dialog.open(RanoutDialogComponent, {
            width: '540px',
            data: {}
          });
          dialogRef.afterClosed().subscribe(result => {
            if (result && result === 'yes') {
            }
          });
        }
      }
    )
  }

  search(page: number): void {
    if (this.isSearching) return;
    console.log(this.userId);
    this.onSearching.emit(true);
    this.isSearching = true;
    this.query.fromDate = this.fromDate ? this.fromDate.toISOString().split('T')[0] : '';
    this.query.toDate = this.toDate ? this.toDate.toISOString().split('T')[0] : '';
    this.query.minLikes = this.likeRange.from === this.likeRange.options.floor ? '' : this.likeRange.from;
    this.query.maxLikes = this.likeRange.to === this.likeRange.options.ceil ? '' : this.likeRange.to;
    this.query.minComments = this.commentRange.from === this.commentRange.options.floor ? '' : this.commentRange.from;
    this.query.maxComments = this.commentRange.to === this.commentRange.options.ceil ? '' : this.commentRange.to;
    this.query.page = page;
    
    let params = '' +
      (this.query.keyword ? 'keyword=' + this.query.keyword + '&' : '') +
      (this.query.category ? 'category=' + this.query.category + '&' : '') +
      (this.query.type ? 'type=' + this.query.type + '&' : '') +
      (this.query.platform ? 'platform=' + this.query.platform + '&' : '') +
      (this.query.fromDate ? 'fromDate=' + this.query.fromDate + '&' : '') +
      (this.query.toDate ? 'toDate=' + this.query.toDate + '&' : '') +
      (this.query.minLikes ? 'minLikes=' + this.query.minLikes + '&' : '') +
      (this.query.maxLikes ? 'maxLikes=' + this.query.maxLikes + '&' : '') +
      (this.query.minComments ? 'minComments=' + this.query.minComments + '&' : '') +
      (this.query.maxComments ? 'maxComments=' + this.query.maxComments + '&' : '') +
      (this.query.filter ? 'filter=' + this.query.filter + '&' : '') +
      (this.query.sort ? 'sort=' + this.query.sort + '&' : '') +
      (this.query.page ? 'page=' + this.query.page : '');

    if (params && !this.userId) {
      let redirect = document.location.origin + '/ads?' + params;
      window.location.href = '/signin?redirect=' + redirect.replace('?', '%3F');
      return;
    }

    window.location.href = '/ads?' + params;
  }

  searchAll(page: number): void {
    this.query.filter = 'all';
    this.search(page);
  }

  searchSaved(page: number): void {
    this.query.filter = 'saved';
    this.search(page);
  }

  searchTracked(page: number): void {
    this.query.filter = 'tracked';
    this.search(page);
  }

  onSortChange(sort: string) : void {
    this.query.sort = sort;
    this.search(0);
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
