import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { User } from 'src/app/core/models/User';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit {

  user: User;
  token: string;
  expandPlan: string;

  products: any = {
    basic: {
      id: 'basic_plan',
      name: 'Basic plan',
      price: 9,
      currency: 'USD'
    },
    premium: {
      id: 'premium_plan',
      name: 'Premium plan',
      price: 49,
      currency: 'USD'
    }
  };

  periods: any[] = [
    {
      months: 1,
      discount: 0,
      desc: ''
    },
    {
      months: 3,
      discount: 15,
      desc: ''
    },
    {
      months: 6,
      discount: 30,
      desc: 'Popular choice'
    },
    {
      months: 12,
      discount: 45,
      desc: 'Best value'
    }
  ];
  
  period: number = this.periods[2];
  invoice: any = {
    productId: '',
    productName: '',
    period: 0,
    originAmount: 0,
    amount: 0,
    percentDiscount: 0
  }

  constructor() { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
    this.token = localStorage.getItem('token');
  }

  round(x): number {
    return Math.round(x);
  }

  toggleBasicPlan(plan: any, period: any) {
    if (this.user && this.token) {
      if (this.expandPlan === 'BASIC') {
        this.expandPlan = '';
      } else {
        this.expandPlan = 'BASIC';
        this.initInvoice(plan, period);
      }
    } else {
      window.location.href = '/signin';
    }
  }

  togglePremiumPlan(plan: any, period: any) {
    if (this.user && this.token) {
      if (this.expandPlan === 'PREMIUM') {
        this.expandPlan = '';
      } else {
        this.expandPlan = 'PREMIUM';
        this.initInvoice(plan, period);
      }
    } else {
      window.location.href = '/signin';
    }
  }

  encodeJsonStringify(json: any): string {
    return btoa(JSON.stringify(json));
  }

  initInvoice(plan: any, period: any): void {
    this.invoice = {
      productId: plan.id,
      productName: plan.name,
      period: period.months,
      originAmount: plan.price * period.months,
      amount: this.round(plan.price * period.months * (1 - period.discount / 100)),
      percentDiscount: period.discount
    }
  }


}
