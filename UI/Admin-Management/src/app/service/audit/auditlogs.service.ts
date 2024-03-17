import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuditlogsService {

  constructor(private http: HttpClient) { }

  getTMLogs(pageNumber: number, pageSize: number): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/audit/logs`, {
      params: {
        pageNumber: pageNumber,
        pageSize: pageSize
      }
    });
  }

  getPMLogs(pageNumber: number, pageSize: number): Observable<any> {
    return this.http.get(`${environment.productProxy}/admin/audit/logs`, {
      params: {
        pageNumber: pageNumber,
        pageSize: pageSize
      }
    });
  }

}
