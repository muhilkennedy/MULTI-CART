import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getCategories(): Observable<any> {
    return this.http.get(`${environment.productProxy}/category`);
  }

  createCategory(body: any): Observable<any> {
    return this.http.post(`${environment.productProxy}/admin/category`, body);
  }

  deleteCategory(id: any): Observable<any> {
    return this.http.delete(`${environment.productProxy}/admin/category/${id}`);
  }

}
