import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../../core/models/User';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private router: Router) { }

  user: User = null;
  ngOnInit(): void {
    if (localStorage.getItem('token') && localStorage.getItem('user')) {
      // logged in so return true
      this.user = JSON.parse(localStorage.getItem('user'));
    }
  }

  signout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/';
  }

}
