import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { User } from 'src/app/core/models/User';
import { SubscriptionPlanService } from 'src/app/core/services/subscription-plan.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit {

  user: User;
  token: string;
  subscriptionPlans: any = {};

  constructor(private subscriptionPlanService: SubscriptionPlanService) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
    this.token = localStorage.getItem('token');

    this.loadSubscriptionPlans();
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

  round(x): number {
    return Math.round(x);
  }

  encodeJsonStringify(json: any): string {
    return btoa(JSON.stringify(json));
  }

  initInvoice(plan: any, months: number): any {
    let period = plan.periods.find(p => p.months === months);
    return {
      planId: plan.id,
      planName: plan.name,
      period: period.months,
      originAmount: plan.price * period.months,
      amount: this.round(plan.price * period.months * (1 - period.discount / 100)),
      percentDiscount: period.discount
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

  redirectToSignup() {
    window.location.href = '/signup';
  }

}
