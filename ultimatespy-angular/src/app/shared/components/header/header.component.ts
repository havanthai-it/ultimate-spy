import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../../core/models/User';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isAppPage: boolean = false;
  user: User = null;

  constructor(private router: Router) { }

  ngOnInit(): void {
    if (this.router.url.startsWith('/ads')) {
      this.isAppPage = true;
    } else if (this.router.url.startsWith('/#feature')) {
      setTimeout(() => {
        this.scrollToView('feature');
      }, 0);
    } else if (this.router.url.startsWith('/#pricing')) {
      setTimeout(() => {
        this.scrollToView('pricing');
      }, 0);
    }

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

  scrollToView(id: string): void {
    let eleRef = document.getElementById(id);
    window.scrollTo(eleRef.offsetLeft, eleRef.offsetTop);
  }

}
