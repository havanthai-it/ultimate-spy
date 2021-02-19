import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private http: HttpClient) { }

  headers(): HttpHeaders {
    let token = localStorage.getItem('token');
    let user = localStorage.getItem('user');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'X-User-Id': `${user ? JSON.parse(user).id : ''}`
    });
  }

  cancelSubscription(subscriptionId: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/paypal/subscription/${subscriptionId}`;
    return this.http.delete(url, { headers: this.headers() });
  }

}
