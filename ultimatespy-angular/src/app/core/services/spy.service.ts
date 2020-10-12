import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { FacebookPostQuery } from '../models/FacebookPostQuery';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SpyService {

  constructor(private http: HttpClient) { }

  headersGet(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
  }

  searchFacebookPost(query: FacebookPostQuery): Observable<any> {
    const url = `${environment.serviceUrl}/api/facebook-post?`
                  + 'fromDate=' + (query.fromDate ? query.fromDate : '')
                  + '&toDate=' + (query.toDate ? query.toDate : '')
                  + '&page=' + (query.page ? query.page : 0)
                  + '&pageSize=' + (query.pageSize ? query.pageSize : 30)
                  + '&keyword=' + (query.keyword ? query.keyword : '')
                  + '&pixelId=' + (query.pixelId ? query.pixelId : '')
                  + '&facebookPageId=' + (query.facebookPageId ? query.facebookPageId : '')
                  + '&category=' + (query.category ? query.category : '')
                  + '&type=' + (query.type ? query.type : '')
                  + '&country=' + (query.country ? query.country : '')
                  + '&language=' + (query.language ? query.language : '')
                  + '&website=' + (query.website ? query.website : '')
                  + '&platform=' + (query.platform ? query.platform : '')
                  + '&minLikes=' + (query.minLikes ? query.minLikes : '')
                  + '&maxLikes=' + (query.maxLikes ? query.maxLikes : '')
    return this.http.get(url, { headers: this.headersGet() });
  }
}
