import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserPostService {

  constructor(private http: HttpClient) { }

  headers(): HttpHeaders {
    let token = localStorage.getItem('token');
    let user = localStorage.getItem('user');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'X-User-Id': `${user ? JSON.parse(user).id : ''}`
    });
  }

  getListFacebookPostId(userId: string, type: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/user/${userId}/post?type=${type}`;
    return this.http.get(url, { headers: this.headers() });
  }

  insert(userId: string, facebookPostId: string, type: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/user/${userId}/post?`
                  + `facebookPostId=${facebookPostId}`
                  + `&type=${type}`;
    return this.http.post(url, {}, { headers: this.headers() });
  }

  delete(userId: string, facebookPostId: string, type: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/user/${userId}/post?`
                  + `facebookPostId=${facebookPostId}`
                  + `&type=${type}`;
    return this.http.delete(url, { headers: this.headers() });
  }
}
