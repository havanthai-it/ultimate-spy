import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-spy-search-result',
  templateUrl: './spy-search-result.component.html',
  styleUrls: ['./spy-search-result.component.scss']
})
export class SpySearchResultComponent implements OnInit {
  @Input() data: any[];

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    console.log('ngOnChanges: ' + this.data);
  }

}
