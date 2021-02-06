import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  pathName: string = '';

  constructor() { }

  ngOnInit(): void {
    this.pathName = window.location.pathname;
  }

  isActive(path) {
    return this.pathName.startsWith(path);
  }

}
