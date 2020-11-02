import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit {

  user: any;
  token: string;
  expandPlan: string;
  
  BASIC_PRICE: number = 9;
  PREMIUM_PRICE: number = 49;

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

  constructor() { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
    this.token = localStorage.getItem('token');
  }

  round(x): number {
    return Math.round(x);
  }

  toggleBasicPlan() {
    if (this.expandPlan === 'BASIC') {
      this.expandPlan = '';
    } else {
      this.expandPlan = 'BASIC';
    }
  }

  togglePremiumPlan() {
    if (this.expandPlan === 'PREMIUM') {
      this.expandPlan = '';
    } else {
      this.expandPlan = 'PREMIUM';
    }
  }

}
