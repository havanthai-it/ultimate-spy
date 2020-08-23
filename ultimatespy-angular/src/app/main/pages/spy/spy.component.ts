import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-spy',
  templateUrl: './spy.component.html',
  styleUrls: ['./spy.component.scss']
})
export class SpyComponent implements OnInit {
  searchResult: any[];

  constructor() { }

  ngOnInit(): void {
  }
  
  setSearchResult(searchResult: any[]): void {
    this.searchResult = searchResult;
  }

}
