import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  headers(): HttpHeaders {
    let token = localStorage.getItem('token');
    let user = localStorage.getItem('user');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'X-User-Id': `${user ? JSON.parse(user).id : ''}`
    });
  }

  getByUser(userId: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/payment?userId=${userId}`;
    return this.http.get(url, { headers: this.headers() });
  }

  insert(payment: any): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/payment`;
    return this.http.post(url, payment, { headers: this.headers() });
  }

  update(payment: any): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/payment/${payment.id}`;
    return this.http.patch(url, payment, { headers: this.headers() });
  }

}
