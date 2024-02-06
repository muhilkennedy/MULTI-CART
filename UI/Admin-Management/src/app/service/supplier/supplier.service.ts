import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {

  constructor(private http: HttpClient) { }

  createSupplier(body: any): Observable<any> {
    return this.http.post<any>(`${environment.productProxy}/supplier`, body);
  }

  getAllSupplier(): Observable<any> {
    return this.http.get<any>(`${environment.productProxy}/supplier`);
  }

}
