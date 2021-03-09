import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private http: HttpClient) { }

  cancelSubscription(subscriptionId: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/paypal/subscription/${subscriptionId}`;
    return this.http.delete(url, {});
  }

}
