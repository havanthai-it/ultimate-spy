import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PromotionService {

  constructor(private http: HttpClient) { }

  get(code: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/v1/promotion/code/${code}`;
    return this.http.get(url, {});
  }

}
