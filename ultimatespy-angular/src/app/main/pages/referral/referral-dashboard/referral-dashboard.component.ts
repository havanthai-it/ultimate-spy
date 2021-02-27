import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-referral-dashboard',
  templateUrl: './referral-dashboard.component.html',
  styleUrls: ['./referral-dashboard.component.scss']
})
export class ReferralDashboardComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle('AdsCrawlr Referral Program | Dashboard');
  }

  ngOnInit(): void {
  }

}
