import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';
import { UserSubscription } from '../models/UserSubscription';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  headers(): HttpHeaders {
    let token = localStorage.getItem('token');
    let user = localStorage.getItem('user');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'X-User-Id': `${user ? JSON.parse(user).id : ''}`
    });
  }

  authenticate(username: string, password: string): Observable<any> {
    const requestData = {
      username: username,
      password: password
    }
    return this.http.post(`${environment.serviceUrl}/api/v1/authenticate`, requestData);
  }

  signup(user: any): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/v1/user`, user);
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/v1/forgot-password/${email}`, {});
  }

  get(id: string, email: string): Observable<any> {
    return this.http.get<User>(`${environment.serviceUrl}/api/v1/user?id=${id}&email=${email}`, { headers: this.headers() });
  }

  update(user: User): Observable<any> {
    return this.http.put(`${environment.serviceUrl}/api/v1/user`, user, { headers: this.headers() });
  }

  subscribe(userSubscription: UserSubscription): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/v1/user/${userSubscription.userId}/subscription`, userSubscription, { headers: this.headers() });
  }

  confirm(userId: string, confirmId: string): Observable<any> {
    return this.http.patch(`${environment.serviceUrl}/api/v1/user/${userId}/confirm/${confirmId}`, {}, { headers: new HttpHeaders() });
  }

  reset(email: string, password: string): Observable<any> {
    const data = {
      email: email,
      password: password
    }
    return this.http.patch(`${environment.serviceUrl}/api/v1/reset-password`, data, { headers: new HttpHeaders() });
  }

}
