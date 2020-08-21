import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-spy-search',
  templateUrl: './spy-search.component.html',
  styleUrls: ['./spy-search.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpySearchComponent implements OnInit {

  constructor() { }

  query: any = {
    keyword: '',
    category: '',
    creative: '',
    country: '',
    language: ''
  }
  lstCategories: any[] = [
    { label: 'Clothes', value: 'Clothes' },
    { label: 'Technology', value: 'Technology' }
  ];
  lstCreatives: any[] = [
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
  
  lstEcomWebsites: any[] = [
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

  }

  saveSearchQuery(): void {
    
  }

}