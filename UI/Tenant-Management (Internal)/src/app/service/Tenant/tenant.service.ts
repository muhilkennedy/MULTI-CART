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

  getAllTenants(): Observable<any> {
    return this.http.get<any>(`${environment.backendProxy}/admin/tenant/alltenants`);
  }

  onboardTenant(body): Observable<any> {
    return this.http.post<any>(`${environment.backendProxy}/admin/tenant/create`, body);
  }

  toggleTenant(uniqueName): Observable<any> {
    return this.http.patch<any>(`${environment.backendProxy}/admin/tenant/toggle`, {}, {
      params: {
        tenantUniqueName: uniqueName
      }
    });
  }

  getSubscriptions(id): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/tenant/subscriptions`, {
      params: {
        tenantId: id
      }});
  }

  renewSubscription(body, tenant): Observable<any> {
    return this.http.put<any>(`${environment.backendProxy}/admin/tenant/addsubscription`, body, {
      params: {
        tenantId: tenant
      }
    })
  }

  uploadLogo(file, tenant): Observable<any> {
    const formData = new FormData(); 
    formData.append("picture", file, file.name); 
    return this.http.post<any>(`${environment.backendProxy}/admin/tenant/logo`, formData, {
      params: {
        tenantId: tenant
      }
    })
  }

  createStorageConfig(body, tenant): Observable<any> {
    return this.http.post<any>(`${environment.backendProxy}/admin/tenant/storage/addconfig`, body, {
      params: {
        tenantId: tenant
      }
    });
  }

}


// getAllDestinationsHierarchy(): Observable<any>{
//   return this.http.get<any>('/destination/tree');
// }

// createDestination(body): Observable<any>{
//   return this.http.post<any>('/admin/destination/create', body);
// }

// uploadDestinationPicture(file, destinationId): Observable<any>{
//   const uploadData = new FormData();
//   uploadData.append("picture", file);
//   uploadData.append("id", destinationId);
//   return this.http.post<any>('/admin/destination/picture', uploadData);
// }

// uploadDestinationPictures(files: any[], destinationId): Observable<any>{
//   const uploadData = new FormData();
//   files.forEach(file => {
//     uploadData.append("pictures", file);
//   })
//   uploadData.append("id", destinationId);
//   return this.http.post<any>('/admin/destination/pictures', uploadData);
// }
