import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-referral-introduction',
  templateUrl: './referral-introduction.component.html',
  styleUrls: ['./referral-introduction.component.scss']
})
export class ReferralIntroductionComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle('AdsCrawlr Referral Program | Introduction');
  }

  ngOnInit(): void {
  }

}
