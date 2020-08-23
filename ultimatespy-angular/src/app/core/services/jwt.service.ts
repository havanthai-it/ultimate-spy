import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { JwtRequest } from '../models/JwtRequest';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor(private http: HttpClient) { }

  authenticate(jwtRequest: JwtRequest): Observable<any> {
    return this.http.post(`${environment.serviceUrl}/api/authenticate`, jwtRequest);
  }

}
