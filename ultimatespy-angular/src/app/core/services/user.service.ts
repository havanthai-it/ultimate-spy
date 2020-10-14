import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  authenticate(username: string, password: string): Observable<any> {
    const requestData = {
      username: username,
      password: password
    }
    return this.http.post(`${environment.serviceUrl}/api/authenticate`, requestData);
  }

  signup(user: any): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/user`, user);
  }

  resetPassword(email: string): Observable<any> {
    return this.http.put(`${environment.serviceUrl}/api/reset-password/${email}`, {});
  }

  get(email: string): Observable<User> {
    return this.http.get<User>(`${environment.serviceUrl}/api/user?email=${email}`);
  }

  update(user: User): Observable<any> {
    return this.http.put(`${environment.serviceUrl}/api/user`, user);
  }

}
