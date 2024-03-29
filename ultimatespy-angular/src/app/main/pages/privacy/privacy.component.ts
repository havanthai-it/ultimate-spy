import { Component, OnInit } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';

@Component({
  selector: 'app-privacy',
  templateUrl: './privacy.component.html',
  styleUrls: ['./privacy.component.scss']
})
export class PrivacyComponent implements OnInit {

  constructor(private titleService: Title, private metaService: Meta) {
    this.titleService.setTitle('AdsCrawlr | Privacy');
  }

  ngOnInit(): void {
  }

}
