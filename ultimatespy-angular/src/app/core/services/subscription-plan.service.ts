import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionPlanService {

  constructor(private http: HttpClient) { }

  getListSubscriptionPlan(): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/subscription-plan`;
    return this.http.get(url, {});
  }
}
