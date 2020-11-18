import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-plan-cta-dialog',
  templateUrl: './plan-cta-dialog.component.html',
  styleUrls: ['./plan-cta-dialog.component.scss']
})
export class PlanCtaDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<PlanCtaDialogComponent>,
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
