import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SpySearchResultItemDialogComponent } from './spy-search-result-item-dialog/spy-search-result-item-dialog.component';
import * as moment from 'moment';

@Component({
  selector: 'app-spy-search-result-item',
  templateUrl: './spy-search-result-item.component.html',
  styleUrls: ['./spy-search-result-item.component.scss']
})
export class SpySearchResultItemComponent implements OnInit, OnChanges {
  @Input() post: any;

  Moment: any = moment;

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
  }

  detail(): void {
    const dialogRef = this.dialog.open(SpySearchResultItemDialogComponent, {
      width: '940px',
      data: this.post
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  extractContent(htmlString: string): string {
    var span = document.createElement('span');
    span.innerHTML = htmlString;
    return span.textContent || span.innerText;
  };

}
