import { DatePipe } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { User } from 'src/app/core/models/User';
import { PaymentService } from 'src/app/core/services/payment.service';
import { SubscriptionPlanService } from 'src/app/core/services/subscription-plan.service';
import { UserService } from 'src/app/core/services/user.service';

declare var paypal;
@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CheckoutComponent implements OnInit {
  @ViewChild('paypal') paypalElement: ElementRef;

  invoice: any = {};
  user: User;
  subscriptionPlans: any = {};
  period: any = {};
  payment: any = {};

  constructor(private activatedRoute: ActivatedRoute,
    private datepipe: DatePipe,
    private userService: UserService,
    private subscriptionPlanService: SubscriptionPlanService,
    private paymentService: PaymentService) { }

  ngOnInit(): void {
    
    this.user = JSON.parse(localStorage.getItem('user'));
    console.log(this.user);

    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.invoice = JSON.parse(atob(params.p));
      this.period = this.invoice.plan.periods.find(p => p.months === this.invoice.period);
      console.log(this.invoice);
    });
    
  }

  ngAfterViewInit(): void {
    this.paymentService.insert({
      userId: this.user.id,
      paymentMethod: 'paypal',
      amount: this.invoice.amount,
      fee: 0,
      tax: 0,
      discount: this.invoice.originAmount - this.invoice.amount,
      originAmount: this.invoice.originAmount,
      currency: 'USD',
      status: 'pending',
      planId: this.invoice.planId
    }).subscribe(
      data => {
        this.payment = data;
        this.initPaypal();
      },
      error => {
        console.log(error);
      }
    );
    
  }

  initPaypal(): void {
    const self = this;
    paypal.Buttons({
      createSubscription: function (data, actions) {  
        return actions.subscription.create({ 
          plan_id: self.getPaypalPlanId(self.invoice.planId, self.invoice.period),  
        });
      },  
      onApprove: function (data, actions) {  
        console.log('onApprove - transaction was approved, but not authorized', data, actions);
        // self.getSubcriptionDetails(data.subscriptionID);  
        self.paymentService.update({
          id: self.payment.id,
          status: 'approved',
          paypalSubscriptionId: data.subscriptionID,
          paypalPlanId: self.getPaypalPlanId(self.invoice.planId, self.invoice.period)
        }).subscribe(
          data => {},
          error => {console.log(error);}
        );
        let now = new Date();
        let from = new Date();
        let to = now.setMonth(now.getMonth() + self.invoice.period);
        self.userService.subscribe({
          userId: self.user.id,
          planId: self.invoice.planId,
          from: self.datepipe.transform(from, 'yyyy-MM-dd'),
          to: self.datepipe.transform(to, 'yyyy-MM-dd'),
          desc: '',
          status: 'approved',
          paypalSubscriptionId: data.subscriptionID,
          paypalPlanId: self.getPaypalPlanId(self.invoice.planId, self.invoice.period)
        }).subscribe(
          data => { setTimeout(() => { window.location.href = '/dashboard/plan' }, 1000) },
          error => {console.log(error);}
        );
      },  
      onCancel: function (data) {  
        // Show a cancel page, or return to cart  
        console.log('OnCancel', data);
        self.paymentService.update({
          id: self.payment.id,
          status: 'canceled',
          paypalSubscriptionId: '',
          paypalPlanId: self.getPaypalPlanId(self.invoice.planId, self.invoice.period)
        }).subscribe(
          data => {},
          error => {console.log(error);}
        );
      },  
      onError: function (err) {  
        // Show an error page here, when an error occurs  
        console.log('OnError', err);
        self.paymentService.update({
          id: self.payment.id,
          status: 'failed',
          paypalSubscriptionId: '',
          paypalPlanId: self.getPaypalPlanId(self.invoice.planId, self.invoice.period)
        }).subscribe(
          data => {},
          error => {console.log(error);}
        );
      }  
    }).render(this.paypalElement.nativeElement);
  }

  // getSubcriptionDetails(subcriptionId) {  
  //   const xhttp = new XMLHttpRequest();  
  //   xhttp.onreadystatechange = function () {  
  //     if (this.readyState === 4 && this.status === 200) {  
  //       console.log(JSON.parse(this.responseText));  
  //       alert(JSON.stringify(this.responseText));  
  //     }  
  //   };  
  //   xhttp.open('GET', 'https://api.sandbox.paypal.com/v1/billing/subscriptions/' + subcriptionId, true);  
  //   xhttp.setRequestHeader('Authorization', ''); // TODO:
  
  //   xhttp.send();  
  // }

  round(x: number): number {
    return Math.round(x);
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

  onChangePeriod(p: any): void {
    this.period = p;
    this.invoice = this.initInvoice(this.invoice.plan, p.months);
  }

  getPaypalPlanId(plan: string, period: number) {
    if (plan === 'basic' && period === 1) {
      return 'P-1J6726964U314624NMAQA6HQ';
    } else if (plan === 'basic' && period === 3) {
      return 'P-4EF61833C0475051RMAQTSWY';
    } else if (plan === 'basic' && period === 6) {
      return 'P-50J72259K8001514NMAQTLZA';
    } else if (plan === 'basic' && period === 12) {
      return 'P-3DH41113N9816535SMAQTTYQ';
    } else if (plan === 'premium' && period === 1) {
      return '';
    } else if (plan === 'premium' && period === 3) {
      return '';
    } else if (plan === 'premium' && period === 6) {
      return '';
    } else if (plan === 'premium' && period === 12) {
      return '';
    }
    return '';
  }

  toFixedIfNecessary(value: any, dp: number): number {
    return +parseFloat(value).toFixed(dp);
  }
}
