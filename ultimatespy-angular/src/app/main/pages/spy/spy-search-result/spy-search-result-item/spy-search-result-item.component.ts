import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SpySearchResultItemDialogComponent } from './spy-search-result-item-dialog/spy-search-result-item-dialog.component';

@Component({
  selector: 'app-spy-search-result-item',
  templateUrl: './spy-search-result-item.component.html',
  styleUrls: ['./spy-search-result-item.component.scss']
})
export class SpySearchResultItemComponent implements OnInit, OnChanges {
  @Input() item: any[];

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    console.log('ngOnChanges: ' + this.item);
  }

  detail(): void {
    const dialogRef = this.dialog.open(SpySearchResultItemDialogComponent, {
      width: '940px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

}
