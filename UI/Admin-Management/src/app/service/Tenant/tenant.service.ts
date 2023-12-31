import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tenant } from 'src/app/model/tenant.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TenantService {

  private tenant: Tenant;

  constructor(private http: HttpClient) {
    this.tenant = new Tenant();
  }

  getCurrentTenant(): Tenant {
    return this.tenant;
  }

  uploadLogo(file: File): Observable<any> {
    const formData = new FormData();
    formData.append("picture", file, file.name);
    return this.http.post<any>(`${environment.backendProxy}/admin/tenant/logo`, formData)
  }

  getMailInboxUrl(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/email/inbox`);
  }

  updateValue(field: string, value: string): Observable<any> {
    return this.http.put<any>(`${environment.backendProxy}/admin/tenant/${field}`, null, {
      params: {
        value: value
      }
    })
  }

}