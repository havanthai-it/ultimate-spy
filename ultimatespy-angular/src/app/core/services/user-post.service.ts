import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserPostService {

  constructor(private http: HttpClient) { }

  getListFacebookPostId(userId: string, type: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/user/${userId}/post?type=${type}`;
    return this.http.get(url, {});
  }

  insert(userId: string, facebookPostId: string, type: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/user/${userId}/post?`
                  + `facebookPostId=${facebookPostId}`
                  + `&type=${type}`;
    return this.http.post(url, {}, {});
  }

  delete(userId: string, facebookPostId: string, type: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/user/${userId}/post?`
                  + `facebookPostId=${facebookPostId}`
                  + `&type=${type}`;
    return this.http.delete(url, {});
  }
}
