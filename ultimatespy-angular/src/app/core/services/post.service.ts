import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { FacebookPostQuery } from '../models/FacebookPostQuery';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  headers(): HttpHeaders {
    let token = localStorage.getItem('token');
    let user = localStorage.getItem('user');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'X-User-Id': `${user ? JSON.parse(user).id : ''}`
    });
  }

  getFacebookPost(id: string): Observable<any> {
    const url = `${environment.serviceUrl}/api/facebook-post/${id}`;
    return this.http.get(url, { headers: this.headers() });
  }

  searchFacebookPost(query: FacebookPostQuery): Observable<any> {
    const url = `${environment.serviceUrl}/api/facebook-post?`
                  + 'fromDate=' + (query.fromDate ? query.fromDate : '')
                  + '&toDate=' + (query.toDate ? query.toDate : '')
                  + '&page=' + (query.page ? query.page : 0)
                  + '&pageSize=' + (query.pageSize ? query.pageSize : 24)
                  + '&keyword=' + (query.keyword ? query.keyword : '')
                  + '&pixelId=' + (query.pixelId ? query.pixelId : '')
                  + '&facebookPageUsername=' + (query.facebookPageUsername ? query.facebookPageUsername : '')
                  + '&category=' + (query.category ? query.category : '')
                  + '&type=' + (query.type ? query.type : '')
                  + '&country=' + (query.country ? query.country : '')
                  + '&language=' + (query.language ? query.language : '')
                  + '&website=' + (query.website ? query.website : '')
                  + '&platform=' + (query.platform ? query.platform : '')
                  + '&minLikes=' + (query.minLikes ? query.minLikes : '')
                  + '&maxLikes=' + (query.maxLikes ? query.maxLikes : '')
                  + '&minComments=' + (query.minComments ? query.minComments : '')
                  + '&maxComments=' + (query.maxComments ? query.maxComments : '')
    return this.http.get(url, { headers: this.headers() });
  }
}
