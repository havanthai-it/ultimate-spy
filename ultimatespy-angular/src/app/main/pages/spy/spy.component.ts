import { Component, OnInit, ViewChild } from '@angular/core';
import { SpySearchComponent } from './spy-search/spy-search.component';

@Component({
  selector: 'app-spy',
  templateUrl: './spy.component.html',
  styleUrls: ['./spy.component.scss']
})
export class SpyComponent implements OnInit {
  @ViewChild('spySearch') spySearchComponent: SpySearchComponent;

  searchResult: any = {};
  listSavedIds: string[] = [];
  listTrackedIds: string[] = [];
  isSearching: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }
  
  setSearchResult(searchResult: any): void {
    this.searchResult = searchResult;
  }

  setListSavedIds(listSavedIds: string[]): void {
    this.listSavedIds = listSavedIds;
  }

  setListTrackedIds(listTrackedIds: string[]): void {
    this.listTrackedIds = listTrackedIds;
  }

  onSearching(isSearching: boolean): void {
    this.isSearching = isSearching;
  }

  search(page: number): void {
    this.spySearchComponent.search(page, true);
  }

  /**
   * i from 0 -> total - 1
   */
  showPage(i: number): boolean {
    if (this.searchResult.pages <= 6) return true;
    if (i === 0 || i === (this.searchResult.pages -  1)) return true;
    if ((i + 1) > (this.searchResult.page - 1) && (i + 1) < (this.searchResult.page + 3)) return true;
    if (this.searchResult.page === 0 && (i + 1) < 4) return true;
    return false;
  }

  showPageEllipsis(i: number): boolean {
    if (this.searchResult.pages > 6 && this.searchResult.page === 0 && (i + 1) === 3) return false;
    if (this.searchResult.pages > 6 && this.searchResult.page === 0 && (i + 1) === 4) return true;
    if (this.searchResult.pages > 6 && i > 0 && i < this.searchResult.pages - 1 && ((i + 1) === this.searchResult.page - 1 || (i + 1) === this.searchResult.page + 3)) return true;
    return false;
  }

}
