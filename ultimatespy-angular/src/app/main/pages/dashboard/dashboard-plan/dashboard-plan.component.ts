import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { PaymentService } from 'src/app/core/services/payment.service';
import { DatePipe } from '@angular/common';
import { SubscriptionPlanService } from 'src/app/core/services/subscription-plan.service';
import { PaypalService } from 'src/app/core/services/paypal.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-dashboard-plan',
  templateUrl: './dashboard-plan.component.html',
  styleUrls: ['./dashboard-plan.component.scss']
})
export class DashboardPlanComponent implements OnInit {

  user: any = {};
  token: string;
  currentSubscription: any;
  payments: Array<any> = [];
  subscriptionPlans: any = {};

  constructor(
    private datePipe: DatePipe,
    private dialog: MatDialog,
    private userService: UserService, 
    private paymentService: PaymentService,
    private subscriptionPlanService: SubscriptionPlanService,
    private paypalService: PaypalService) { }

  ngOnInit(): void {
    this.loadSubscriptionPlans();

    let user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {};
    this.token = localStorage.getItem('token');

    if (user && (user.id || user.email)) {
      this.userService.get(user.id, user.email).subscribe(
        data => {
          this.user = data;
          this.currentSubscription = (data.lstSubscriptions && data.lstSubscriptions.length > 0) ? data.lstSubscriptions[0] : null;
        },
        error => {
          console.log(error);
        }
      );

      this.paymentService.getByUser(user.id).subscribe(
        data => {
          this.payments = data;
        },
        error => {
          console.log(error);
        }
      );
    }
  }
  
  loadSubscriptionPlans() {
    this.subscriptionPlanService.getListSubscriptionPlan().subscribe(
      data => {
        this.subscriptionPlans = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  getCurrentPlanStatus(currentSubscription: any) {
    if (currentSubscription.status === 'approved') {
      const nowDate = new Date();
      nowDate.setHours(0);
      nowDate.setMinutes(0);
      nowDate.setSeconds(0);
      nowDate.setMilliseconds(0);
      const now = this.datePipe.transform(nowDate, 'yyyy-MM-dd');
      return currentSubscription.to >= now ? 'active' : 'expired';
    } else {
      return currentSubscription.status;
    }
    
  }

  cancelSubscription(): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '440px',
      data: {
        title: 'Confirm',
        message: 'Are you sure want to cancel subscription?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result && result === 'yes') {
        this.paypalService.cancelSubscription(this.currentSubscription.paypalSubscriptionId).subscribe(
          data => {
            this.currentSubscription.status = 'canceled';
          },
          error => { console.log(error); }
        );
      }
    });
  }

  renewSubscription(): void {
    this.redirectToCheckout(this.subscriptionPlans[this.user.plan]);
  }

  initInvoice(plan: any, months: number): any {
    let period = plan.periods.find(p => p.months === months);
    return {
      planId: plan.id,
      planName: plan.name,
      period: period.months,
      originAmount: plan.price * period.months,
      amount: this.toFixedIfNecessary(plan.price * period.months * (1 - period.discount / 100), 2),
      percentDiscount: period.discount,
      plan: plan
    }
  }

  redirectToCheckout(plan: any) {
    if (this.user && this.token) {
      let invoice = this.initInvoice(plan, 6);
      let encodedInvoice = this.encodeJsonStringify(invoice);
      window.location.href = '/checkout?p=' + encodedInvoice;
    } else {
      window.location.href = '/signin';
    }
  }

  toFixedIfNecessary(value: any, dp: number): number {
    return +parseFloat(value).toFixed(dp);
  }

  encodeJsonStringify(json: any): string {
    return btoa(JSON.stringify(json));
  }

}
