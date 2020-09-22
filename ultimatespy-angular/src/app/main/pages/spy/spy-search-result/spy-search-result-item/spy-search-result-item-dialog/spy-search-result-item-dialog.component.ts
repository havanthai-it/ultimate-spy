import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FacebookPost } from '../../../../../../core/models/FacebookPost';

@Component({
  selector: 'app-spy-search-result-item-dialog',
  templateUrl: './spy-search-result-item-dialog.component.html',
  styleUrls: ['./spy-search-result-item-dialog.component.scss']
})
export class SpySearchResultItemDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<SpySearchResultItemDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: FacebookPost) { }

  ngOnInit(): void {
  }

  close(): void {
    this.dialogRef.close();
  }

}
