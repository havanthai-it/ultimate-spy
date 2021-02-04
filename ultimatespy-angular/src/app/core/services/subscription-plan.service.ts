import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionPlanService {

  constructor(private http: HttpClient) { }

  headers(): HttpHeaders {
    let token = localStorage.getItem('token');
    let user = localStorage.getItem('user');
    if (token && user) {
      return new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'X-User-Id': `${user ? JSON.parse(user).id : ''}`
      });
    } else {
      return new HttpHeaders({
        'X-User-Id': `${user ? JSON.parse(user).id : ''}`
      });
    }
  }

  getListSubscriptionPlan(): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/subscription-plan`;
    return this.http.get(url, { headers: this.headers() });
  }
}
