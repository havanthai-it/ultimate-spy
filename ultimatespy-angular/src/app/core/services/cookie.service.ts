import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CookieService {

  constructor() { }

  setCookie(name: string, value: string, days: number): void {
    let expires = '';
    if (days) {
      let now = new Date();
      now.setTime(now.getTime() + (days * 24 * 60 * 60 * 1000));
      expires = '; expires=' + now.toUTCString();
    }
    document.cookie = name + '=' + (value || '') + expires + '; path=/';
  }
  getCookie(name: string): string {
    var nameEQ = name + '=';
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') c = c.substring(1, c.length);
      if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return '';
  }
  eraseCookie(name: string): void {
    document.cookie = name + '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
  }
}
