import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-referral',
  templateUrl: './referral.component.html',
  styleUrls: ['./referral.component.scss']
})
export class ReferralComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle('AdsCrawlr | Referral Program');
  }

  ngOnInit(): void {
  }

}
