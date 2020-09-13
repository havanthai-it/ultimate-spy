import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  authenticate(username, password): Observable<any> {
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

}
