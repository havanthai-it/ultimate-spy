import { DatePipe } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';
import { ActivatedRoute, Params } from '@angular/router';
import { User } from 'src/app/core/models/User';
import { PaymentService } from 'src/app/core/services/payment.service';
import { SubscriptionPlanService } from 'src/app/core/services/subscription-plan.service';
import { PromotionService } from 'src/app/core/services/promotion.service';
import { UserService } from 'src/app/core/services/user.service';
import { environment } from 'src/environments/environment';

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
  hasPromotion: boolean = false;
  promotionCode: string;
  promotionDiscountPercent: number = 0;

  constructor(private activatedRoute: ActivatedRoute,
    private datepipe: DatePipe,
    private titleService: Title,
    private metaService: Meta,
    private userService: UserService,
    private subscriptionPlanService: SubscriptionPlanService,
    private paymentService: PaymentService,
    private promotionService: PromotionService) {
      this.titleService.setTitle('AdsCrawlr | Checkout');
    }

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
          plan_id: self.getPaypalPlanId(self.invoice.planId, self.invoice.period, self.promotionDiscountPercent),  
        });
      },  
      onApprove: function (data, actions) {  
        console.log('onApprove - transaction was approved, but not authorized', data, actions);
        // self.getSubcriptionDetails(data.subscriptionID);  
        self.paymentService.update({
          id: self.payment.id,
          status: 'approved',
          paypalSubscriptionId: data.subscriptionID,
          paypalPlanId: self.getPaypalPlanId(self.invoice.planId, self.invoice.period, self.promotionDiscountPercent)
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
          paypalPlanId: self.getPaypalPlanId(self.invoice.planId, self.invoice.period, self.promotionDiscountPercent)
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
          paypalPlanId: self.getPaypalPlanId(self.invoice.planId, self.invoice.period, self.promotionDiscountPercent)
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
          paypalPlanId: self.getPaypalPlanId(self.invoice.planId, self.invoice.period, self.promotionDiscountPercent)
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

  getPromotionDiscountPercent(): void {
    if (this.promotionCode) {
      this.promotionService.get(this.promotionCode.toUpperCase()).subscribe(
        data => {
          this.promotionDiscountPercent = data;
          this.invoice.amount = this.invoice.amount - this.invoice.originAmount * this.promotionDiscountPercent / 100
        },
        error => {}
      );
    }
  }

  getPaypalPlanId(plan: string, period: number, discountPercent: number) {
    if (plan === 'basic' && period === 1 && discountPercent === 0) {
      return environment.paypal.plan.P9M1D0;
    } else if (plan === 'basic' && period === 3 && discountPercent === 0) {
      return environment.paypal.plan.P9M3D10;
    } else if (plan === 'basic' && period === 6 && discountPercent === 0) {
      return environment.paypal.plan.P9M6D20;
    } else if (plan === 'basic' && period === 12 && discountPercent === 0) {
      return environment.paypal.plan.P9M12D30;
    } else if (plan === 'premium' && period === 1 && discountPercent === 0) {
      return environment.paypal.plan.P49M1D0;
    } else if (plan === 'premium' && period === 3 && discountPercent === 0) {
      return environment.paypal.plan.P49M3D10;
    } else if (plan === 'premium' && period === 6 && discountPercent === 0) {
      return environment.paypal.plan.P49M6D20;
    } else if (plan === 'premium' && period === 12 && discountPercent === 0) {
      return environment.paypal.plan.P49M12D30;
    }

    // Testing
    else if (period === 1 && discountPercent >= 89) {
      return environment.paypal.plan.P1M1D0;
    } else if (period === 3 && discountPercent >= 89) {
      return environment.paypal.plan.P1M3D0;
    }
    return '';
  }

  toFixedIfNecessary(value: any, dp: number): number {
    return +parseFloat(value).toFixed(dp);
  }
}
