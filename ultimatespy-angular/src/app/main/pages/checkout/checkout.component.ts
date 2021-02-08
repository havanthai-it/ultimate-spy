import { Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { User } from 'src/app/core/models/User';
import { SubscriptionPlanService } from 'src/app/core/services/subscription-plan.service';

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
    
  }

  ngAfterViewInit(): void {
    this.initPaypal();
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
      },  
      onCancel: function (data) {  
        // Show a cancel page, or return to cart  
        console.log('OnCancel', data);
      },  
      onError: function (err) {  
        // Show an error page here, when an error occurs  
        console.log('OnError', err);
      }  
    }).render(this.paypalElement.nativeElement);
  }

  getSubcriptionDetails(subcriptionId) {  
    const xhttp = new XMLHttpRequest();  
    xhttp.onreadystatechange = function () {  
      if (this.readyState === 4 && this.status === 200) {  
        console.log(JSON.parse(this.responseText));  
        alert(JSON.stringify(this.responseText));  
      }  
    };  
    xhttp.open('GET', 'https://api.sandbox.paypal.com/v1/billing/subscriptions/' + subcriptionId, true);  
    xhttp.setRequestHeader('Authorization', ''); // TODO:
  
    xhttp.send();  
  }

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
