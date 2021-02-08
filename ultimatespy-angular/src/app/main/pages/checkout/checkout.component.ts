import { Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { ICreateOrderRequest, ICreateSubscriptionRequest, IPayPalConfig } from 'ngx-paypal';
import { User } from 'src/app/core/models/User';
import { SubscriptionPlanService } from 'src/app/core/services/subscription-plan.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CheckoutComponent implements OnInit {
  public payPalConfig?: IPayPalConfig;

  invoice: any = {};
  user: User;
  subscriptionPlans: any = {};
  period: any = {};

  constructor(private activatedRoute: ActivatedRoute,
    private subscriptionPlanService: SubscriptionPlanService) { }

  ngOnInit(): void {
    
    this.user = JSON.parse(localStorage.getItem('user'));
    console.log(this.user);

    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.invoice = JSON.parse(atob(params.p));
      this.period = this.invoice.plan.periods.find(p => p.months === this.invoice.period);
      console.log(this.invoice);
    });

    this.initPaypalConfig();
    
  }

  initPaypalConfig(): void {
    this.payPalConfig = {
      currency: 'USD',
      clientId: 'AYKZwJ4qq1Il0kwhIMwAyL-aA9pJpyHJT9TIZV_eSQnxVaaqIy_e5NVBd_zffeRVm2HjzizfUTYDXAdC',
      advanced: {
        commit: 'true'
      },
      style: {
        label: 'paypal',
        layout: 'vertical',
        size: 'small'
      },
      vault: 'true',
      createSubscription: (data, actions) => {
        return actions.subscription.create({  
          plan_id: this.getPaypalPlanId(this.invoice.planId, this.invoice.period),  
        });  
      },
      onApprove: (data, actions) => {
        console.log('onApprove - transaction was approved, but not authorized', data, actions);
        actions.order.get().then(details => {
          console.log('onApprove - you can get full order details inside onApprove: ', details);
        });
      },
      onClientAuthorization: (data) => {
        console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
      },
      onCancel: (data, actions) => {
        console.log('OnCancel', data, actions);
      },
      onError: err => {
        console.log('OnError', err);
      },
      onClick: (data, actions) => {
        console.log('onClick', data, actions);
      },
    };
  }

  round(x): number {
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
      amount: Math.round(plan.price * period.months * (1 - period.discount / 100)),
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

}
