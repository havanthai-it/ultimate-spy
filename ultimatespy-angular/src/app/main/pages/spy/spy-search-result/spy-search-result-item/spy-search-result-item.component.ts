import { Component, OnInit, Input, OnChanges } from '@angular/core';

@Component({
  selector: 'app-spy-search-result-item',
  templateUrl: './spy-search-result-item.component.html',
  styleUrls: ['./spy-search-result-item.component.scss']
})
export class SpySearchResultItemComponent implements OnInit, OnChanges {
  @Input() item: any[];

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    console.log('ngOnChanges: ' + this.item);
  }

}
