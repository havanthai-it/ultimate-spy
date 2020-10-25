import { Component, OnInit, ViewEncapsulation, Output, EventEmitter } from '@angular/core';
import { SpyService } from '../../../../core/services/spy.service';
import { FacebookPostQuery } from '../../../../core/models/FacebookPostQuery';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-spy-search',
  templateUrl: './spy-search.component.html',
  styleUrls: ['./spy-search.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpySearchComponent implements OnInit {
  @Output() searchResult = new EventEmitter<any[]>();
  
  constructor(private spyService: SpyService) {
  }

  searchQuery: string;
  total: number = 0;
  submited: boolean = false;

  likeRange: any = {
    from: 100,
    to: 10000,
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
    from: 100,
    to: 10000,
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
    pageSize: 30,
    keyword: '',
    pixelId: '',
    facebookPageId: '',
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
    this.fromDate.setDate(this.now.getDate() - 365);
  }

  search(): void {
    console.log(this.query);
    if (this.submited) return;
    this.submited = true;
    this.spyService.searchFacebookPost(this.query).subscribe(
      data => {
        this.total = data.total;
        this.searchResult.emit(data);
        this.submited = false;
      },
      error => {
        console.log(error);
        this.submited = false;
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
