import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReferralService {

  constructor(private http: HttpClient) { }

  postReferral(body: any): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/v1/referral`, body, {});
  }

  summary(referrerId: string): Observable<any> {
    return this.http.get(`${environment.serviceUrl}/api/v1/referral-summary?referrerId=` + referrerId, {});
  }

  listRequestPayout(referrerId: string): Observable<any> {
    return this.http.get(`${environment.serviceUrl}/api/v1/referral-request-payout/` + referrerId, {});
  }

  postRequestPayout(referrerId: string): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/v1/referral-request-payout/` + referrerId, {}, {});
  }

  postReferrerInfo(id: string, code: string, paypalName: string, paypalAccount: string): Observable<any> {
    const body = {
      id: id,
      code: code,
      paypalName: paypalName,
      paypalAccount: paypalAccount
    };
    return this.http.post(`${environment.serviceUrl}/api/v1/referrer`, body, {});
  }

  putReferrerInfo(id: string, code: string, paypalName: string, paypalAccount: string): Observable<any> {
    const body = {
      id: id,
      code: code,
      paypalName: paypalName,
      paypalAccount: paypalAccount
    };
    return this.http.put(`${environment.serviceUrl}/api/v1/referrer/${id}`, body, {});
  }

}
