import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Title } from '@angular/platform-browser';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/core/models/User';
import { ReferralService } from 'src/app/core/services/referral.service';
import { UserService } from 'src/app/core/services/user.service';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { ReferralAgreementDialogComponent } from '../referral-agreement-dialog/referral-agreement-dialog.component';

declare var $: any;
@Component({
  selector: 'app-referral-dashboard',
  templateUrl: './referral-dashboard.component.html',
  styleUrls: ['./referral-dashboard.component.scss']
})
export class ReferralDashboardComponent implements OnInit {

  displayedColumns: string[] = ['no', 'email', 'paymentAmount', 'commissionAmount', 'dateCreate'];
  dataSource = new MatTableDataSource<any>();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  user: User;
  token: string;
  data: any = {};
  requestPayouts: any[] = [];
  referralLink: string;

  enableEditPaypalAccount: boolean = false;
  paypalName: string;
  paypalAccount: string;
  paypalAccountForm = new FormGroup({
    paypalName: new FormControl('', [Validators.required]),
    paypalAccount: new FormControl('', [Validators.required]),
  });

  get f() {
    return this.paypalAccountForm.controls;
  }

  constructor(private titleService: Title,
    private dialog: MatDialog,
    private toastr: ToastrService,
    private userService: UserService,
    private referralService: ReferralService) {
    this.titleService.setTitle('AdsCrawlr Referral Program | Dashboard');
  }

  ngOnInit(): void {
    $('[data-toggle="tooltip"]').tooltip();

    this.user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null;
    this.token = localStorage.getItem('token');

    this.referralLink = 'https://adscrawlr.com?ref=' + this.user.code;
    this.paypalName = this.user.paypalName;
    this.paypalAccount = this.user.paypalAccount;
    if (this.user && this.token && !this.user.code) {
      this.showReferralAggrement();
    }

    if (this.user && this.token) {
      this.loadReferralSummary();
      this.loadReferralRequestPayout();
    }
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  showReferralAggrement() {
    const dialogRef = this.dialog.open(ReferralAgreementDialogComponent, {
      width: '1024px',
      maxWidth: 'calc(100vw - 30px)',
      disableClose: true,
      data: {}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result && result === 'yes') {
        this.referralService.postReferrerInfo(this.user.id, '', '', '').subscribe(
          data => {
            localStorage.setItem('user', JSON.stringify(this.user));
          },
          error => { }
        );
      } else {
        window.location.href = '/referral';
      }
    });
  }

  loadReferralSummary() {
    this.referralService.summary(this.user.id).subscribe(
      data => {
        this.data = data;
        this.dataSource = new MatTableDataSource<any>(data.payReferrals);
      },
      error => { }
    );
  }

  loadReferralRequestPayout() {
    this.referralService.listRequestPayout(this.user.id).subscribe(
      data => {
        this.requestPayouts = data;
      },
      error => { }
    );
  }

  requestPayout() {
    if (this.data.totalOwed && this.data.totalOwed < 20) {
      alert('You must reach the minimum threshold of $20.');
      return;
    }
    if (this.requestPayouts.length > 0) {
      alert('You\'ve already requested payout.');
      return;
    }
    if (!this.user.paypalName || !this.user.paypalAccount) {
      alert('Please update add you paypal account before request payout.');
      return;
    }

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '440px',
      data: {
        title: 'Confirm',
        message: 'Are you sure want to request payout?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result && result === 'yes') {
        this.referralService.postRequestPayout(this.user.id).subscribe(
          data => {
            this.requestPayouts.push({
              id: 0,
              referrerId: this.user.id,
              status: 'pending'
            });
          },
          error => { }
        );
      }
    });
  }

  savePaypalAccount() {
    if (!this.paypalName || !this.paypalAccount) {
      return;
    }

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '440px',
      data: {
        title: 'Confirm',
        message: 'Are you sure want to save your Paypal account information?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result && result === 'yes') {
        this.referralService.putReferrerInfo(this.user.id, this.user.code, this.paypalName, this.paypalAccount).subscribe(
          data => {
            this.user.paypalName = this.paypalName;
            this.user.paypalAccount = this.paypalAccount;
            localStorage.setItem('user', JSON.stringify(this.user));
            this.enableEditPaypalAccount = false;
          },
          error => { }
        );
      }
    });
  }

  copy() {
    const selBox = document.createElement('textarea');
    selBox.style.position = 'fixed';
    selBox.style.left = '0';
    selBox.style.top = '0';
    selBox.style.opacity = '0';
    selBox.value = this.referralLink;
    document.body.appendChild(selBox);
    selBox.focus();
    selBox.select();
    document.execCommand('copy');
    document.body.removeChild(selBox);
    this.toastr.success('Copied', '');
  }

}
