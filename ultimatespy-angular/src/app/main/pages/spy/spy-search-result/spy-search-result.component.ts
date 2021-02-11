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
  @Input() isSearching: boolean = false;

  constructor() { }

  ngOnInit(): void {
    
  }

  ngOnChanges(): void {
    console.log('ngOnChanges: ' + this.data);
  }

}
