import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../core/models/User';
import { CookieService } from 'src/app/core/services/cookie.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isAppPage: boolean = false;
  user: User = null;

  constructor(private router: Router, 
    private activatedRoute: ActivatedRoute,
    private cookieService: CookieService) { }

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
      this.user = JSON.parse(localStorage.getItem('user'));
    }

    // Referrer
    this.activatedRoute.queryParams.subscribe(params => {
      let referrer = params['referrer'];
      if (referrer && (!this.user || this.user.plan === 'free')) {
        this.cookieService.setCookie('referrer', referrer, 60);
      }
    });
  }

  signout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/';
  }

  scrollToView(id: string): void {
    if (window.location.pathname !== '/') {
      window.location.href = '/#' + id;
    } else {
      let eleRef = document.getElementById(id);
      window.scrollTo(eleRef.offsetLeft, eleRef.offsetTop);
    }
  }

}
