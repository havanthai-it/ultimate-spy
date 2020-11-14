import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { UserPostService } from 'src/app/core/services/user-post.service';

@Component({
  selector: 'app-spy-search-result',
  templateUrl: './spy-search-result.component.html',
  styleUrls: ['./spy-search-result.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SpySearchResultComponent implements OnInit {
  @Input() data: any = {};
  @Input() listSavedIds: string[] = [];
  @Input() listTrackedIds: string[] = [];
  @Input() isSearching: boolean = false;

  constructor() { }

  ngOnInit(): void {
    
  }

  ngOnChanges(): void {
    console.log('ngOnChanges: ' + this.data);
  }

  isSaved(facebookPostId: string): boolean {
    return this.listSavedIds.length > 0 && this.listSavedIds.indexOf(facebookPostId) > -1;
  }

  isTracked(facebookPostId: string): boolean {
    return this.listTrackedIds.length > 0 && this.listTrackedIds.indexOf(facebookPostId) > -1;
  }

}
