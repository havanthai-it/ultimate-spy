import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as moment from 'moment';

@Component({
  selector: 'app-spy-search-result-item-dialog',
  templateUrl: './spy-search-result-item-dialog.component.html',
  styleUrls: ['./spy-search-result-item-dialog.component.scss']
})
export class SpySearchResultItemDialogComponent implements OnInit {
  @ViewChild('dialogWrapper') dialogWrapper: ElementRef;

  Moment: any = moment;

  constructor(
    public dialogRef: MatDialogRef<SpySearchResultItemDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dialogWrapper.nativeElement.scrollTop = 0;
    }, 0);
  }

  close(): void {
    this.dialogRef.close();
  }

}
