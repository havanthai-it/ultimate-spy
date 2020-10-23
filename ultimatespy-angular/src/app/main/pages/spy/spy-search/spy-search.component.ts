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
  submited: boolean = false;
  minMaxLikes: string;
  minMaxComments: string;

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
      ceil: 10000,
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
    sort: ''
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
  lstLanguages: any[] = [
    { label: 'All', value: '' },
    { label: 'English', value: 'EN' },
    { label: 'Vietnamese', value: 'VI' }
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
    { label: 'All', value: '' },
    { label: 'Date', value: 'EN' },
    { label: 'Performance', value: 'VN' }
  ];
  lstSearchQueries: any[] = [
    {
      label: 'Suggest',
      value: [
        { label: 'POD', value: 'EN' },
        { label: 'Dropship', value: 'VN' }
      ]
    },
    {
      label: 'Your Saved',
      value: [
        { label: 'My search', value: 'EN' }
      ]
    }
  ];

  ngOnInit(): void {
  }

  search(): void {
    if (this.submited) return;
    this.submited = true;
    this.spyService.searchFacebookPost(this.query).subscribe(
      data => {
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

}
