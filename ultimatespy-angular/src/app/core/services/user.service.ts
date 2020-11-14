import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';

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
    return this.http.post(`${environment.serviceUrl}/api/authenticate`, requestData, { headers: this.headers() });
  }

  signup(user: any): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/user`, user, { headers: this.headers() });
  }

  resetPassword(email: string): Observable<any> {
    return this.http.put(`${environment.serviceUrl}/api/reset-password/${email}`, {}, { headers: this.headers() });
  }

  get(email: string): Observable<User> {
    return this.http.get<User>(`${environment.serviceUrl}/api/user?email=${email}`, { headers: this.headers() });
  }

  update(user: User): Observable<any> {
    return this.http.put(`${environment.serviceUrl}/api/user`, user, { headers: this.headers() });
  }

}
