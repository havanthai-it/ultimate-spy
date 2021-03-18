import { Component, OnInit } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  pathName: string = '';

  constructor(private titleService: Title, private metaService: Meta) {
    this.titleService.setTitle('AdsCrawlr | Profile');
  }

  ngOnInit(): void {
    this.pathName = window.location.pathname;
  }

  isActive(path) {
    return this.pathName.startsWith(path);
  }

}
