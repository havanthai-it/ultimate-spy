import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-ranout-dialog',
  templateUrl: './ranout-dialog.component.html',
  styleUrls: ['./ranout-dialog.component.scss']
})
export class RanoutDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<RanoutDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

  close(): void {
    this.dialogRef.close();
  }

  yes(): void {
    this.dialogRef.close('yes');
  }

  no(): void {
    this.dialogRef.close();
  }

}
