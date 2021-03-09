import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-referral-agreement-dialog',
  templateUrl: './referral-agreement-dialog.component.html',
  styleUrls: ['./referral-agreement-dialog.component.scss']
})
export class ReferralAgreementDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ReferralAgreementDialogComponent>,
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
