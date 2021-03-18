import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../core/models/User';
import { CookieService } from 'src/app/core/services/cookie.service';
import { ReferralService } from 'src/app/core/services/referral.service';
import { UserService } from 'src/app/core/services/user.service';

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
    private cookieService: CookieService,
    private referralService: ReferralService,
    private userService: UserService) {
      if (localStorage.getItem('token') && localStorage.getItem('user')) {
        this.user = JSON.parse(localStorage.getItem('user'));
        this.userService.get(this.user.id, '').subscribe(
          data => {
            this.user = data;
            localStorage.setItem('user', JSON.stringify(this.user));
          },
          error => {}
        );
      }
    }

  ngOnInit(): void {
    if (this.router.url === '/ads' || this.router.url === '/ads/' || this.router.url.startsWith('/ads#') || this.router.url.startsWith('/ads/#')) {
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

    // Referral
    this.activatedRoute.queryParams.subscribe(params => {
      let refCode = params['ref'];
      if (refCode) {
        let oldRefCode = this.cookieService.getCookie('ac_ref');
        if (oldRefCode !== refCode) {
          const body = {
            referrerCode: refCode,
            userId: this.user ? this.user.id : '',
            action: 'click'
          };
          this.referralService.postReferral(body).subscribe(
            data => {
              this.cookieService.setCookie('ac_ref', refCode, 60);
            },
            error => {}
          )
        }
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
