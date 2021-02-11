import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubscriberEmailService {

  constructor(private http: HttpClient) { }

  subscribe(email: string): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/v1/subscriber-email`, { email: email });
  }

}
