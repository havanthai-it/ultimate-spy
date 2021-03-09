import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';
import { UserSubscription } from '../models/UserSubscription';
import { CookieService } from './cookie.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  signin(username: string, password: string): Observable<any> {
    const refCode = this.cookieService.getCookie('ac_ref');
    const requestData = {
      type: 'standard',
      username: username,
      password: password
    }
    return this.http.post(`${environment.serviceUrl}/api/v1/authenticate`, requestData, { headers: new HttpHeaders({ 'X-Ref-Code': refCode }) });
  }

  signinGoogle(idToken: string): Observable<any> {
    const refCode = this.cookieService.getCookie('ac_ref');
    const requestData = {
      type: 'google',
      googleIdToken: idToken
    }
    return this.http.post(`${environment.serviceUrl}/api/v1/authenticate`, requestData, { headers: new HttpHeaders({ 'X-Ref-Code': refCode }) });
  }

  signup(user: any): Observable<any> {
    const refCode = this.cookieService.getCookie('ac_ref');
    return this.http.post(`${environment.serviceUrl}/api/v1/user`, user, { headers: new HttpHeaders({ 'X-Ref-Code': refCode }) });
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/v1/forgot-password/${email}`, {});
  }

  get(id: string, email: string): Observable<any> {
    return this.http.get<User>(`${environment.serviceUrl}/api/v1/user?id=${id}&email=${email}`, {});
  }

  update(user: User): Observable<any> {
    return this.http.put(`${environment.serviceUrl}/api/v1/user`, user, {});
  }

  subscribe(userSubscription: UserSubscription): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/v1/user/${userSubscription.userId}/subscription`, userSubscription, {});
  }

  confirm(userId: string, confirmId: string): Observable<any> {
    return this.http.patch(`${environment.serviceUrl}/api/v1/user/${userId}/confirm/${confirmId}`, {}, {});
  }

  reset(email: string, password: string): Observable<any> {
    const data = {
      email: email,
      password: password
    }
    return this.http.patch(`${environment.serviceUrl}/api/v1/reset-password`, data, {});
  }

}
