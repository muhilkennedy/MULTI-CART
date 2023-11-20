import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SiteSettingsService {

  constructor(private http: HttpClient) { }

  getAllEmailConfigurationKeys(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/email/configurationkeys`);
  }

  getAllGCPConfigurationKeys(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/file/gcp/configurationkeys`);
  }
  
  addConfiguartion(body: any): Observable<any> {
    return this.http.post(`${environment.backendProxy}/admin/base/config`, body);
  }

  addConfiguartions(body: any): Observable<any> {
    return this.http.post(`${environment.backendProxy}/admin/base/configs`, body);
  }

  loadNewEmailConfig(){
    return this.http.patch(`${environment.backendProxy}/admin/email/loadconfig`, null);
  }

  loadNewStorageConfig(){
    return this.http.patch(`${environment.backendProxy}/admin/file/gcp/loadconfig`, null);
  }

  getAllSMSConfigurationKeys(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/sms/configurationkeys`);
  }

}
