import { Component, OnInit, ViewEncapsulation, Output, EventEmitter } from '@angular/core';
import { SpyService } from '../../../../core/services/spy.service';
import { FacebookPostQuery } from '../../../../core/models/FacebookPostQuery';

@Component({
  selector: 'app-spy-search',
  templateUrl: './spy-search.component.html',
  styleUrls: ['./spy-search.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpySearchComponent implements OnInit {
  @Output() searchResult = new EventEmitter<any[]>();

  constructor(private spyService: SpyService) { }

  searchQuery: string;
  dateGroup: string;
  submited: boolean = false;

  query: FacebookPostQuery = {
    fromDate: '',
    toDate: '',
    page: 0,
    pageSize: 30,
    keyword: '',
    category: '',
    type: '',
    country: '',
    language: '',
    ecomSoftware: '',
    ecomPlatform: '',
    sort: ''
  }

  lstCategories: any[] = [
    { label: 'Clothes', value: 'Clothes' },
    { label: 'Technology', value: 'Technology' }
  ];
  lstTypes: any[] = [
    { label: 'Image', value: 'Image' },
    { label: 'Video', value: 'Video' }
  ];
  lstLanguages: any[] = [
    { label: 'English', value: 'EN' },
    { label: 'Vietnamese', value: 'VI' }
  ];
  lstCountries: any[] = [
    { label: 'England', value: 'EN' },
    { label: 'Vietnam', value: 'VN' }
  ];
  lstEcomSoftwares: any[] = [
    { label: 'Woocommerce', value: 'EN' },
    { label: 'Shopify', value: 'VN' }
  ];
  
  lstEcomPlatforms: any[] = [
    { label: 'Anazon', value: 'EN' },
    { label: 'Ebay', value: 'VN' }
  ];
  lstSorts: any[] = [
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
