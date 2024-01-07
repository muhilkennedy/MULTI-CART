import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { TenantService } from '../Tenant/tenant.service';
import { environment } from 'src/environments/environment';
import {EventSourcePolyfill} from 'ng-event-source';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserNotficationService {

  constructor(private http: HttpClient, private cookieService: CookieService, private tenantService: TenantService) { }

  getAllUnreadNotifications(): EventSourcePolyfill{
    //we need to call event stream api with header config separately.
    let eventSource: EventSourcePolyfill = new EventSourcePolyfill(`${environment.backendProxy}/notification`, 
                        { 
                          headers: {
                            'X-Tenant': environment.tenantId,
                            'Authorization': `Bearer ${this.cookieService.get("X-Token")}`,
                            'Accept-Language': this.tenantService.getCurrentTenant().locale
                          } 
                        });
    return eventSource;
  }

  getUnreadNotificationsCount(): Observable<any>{
    return this.http.get(`${environment.backendProxy}/notification/unread/count`);
  }

  markAsRead(id: any){
    return this.http.put(`${environment.backendProxy}/notification/${id}/read`, null);
  }

}
