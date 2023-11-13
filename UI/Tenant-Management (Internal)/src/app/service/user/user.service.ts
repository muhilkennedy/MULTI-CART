import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private user: User;

  constructor(private http: HttpClient) {
    this.user = new User();
  }

  getCurrentUser(): User {
    return this.user;
  }

  login(body): Observable<any>  {
    return this.http.post<any>(`${environment.backendProxy}/user/employee/login`, body, { observe: 'response' })
  }

  pingUser(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/employee/ping`);
  }

  fetchSuperUsers(tenantRootId): Observable<any> {
    return this.http.get(`${environment.backendProxy}/employee/fetch/csa`, {
      params: {
        tenantId : tenantRootId
      }
    });
  }

  registerUser(body, tenantRootId): Observable<any>{
    return this.http.post<any>(`${environment.backendProxy}/employee/admin/create`, body, {
      params: {
        tenantId : tenantRootId
      }
    })
  }

  getAllRoles(tenantRootId): Observable<any> {
    return this.http.get(`${environment.backendProxy}/role/fetch`, {
      params: {
        tenantId : tenantRootId
      }
    });
  }

  toggleUserState(tenantRootId): Observable<any>  {
    return this.http.put<any>(`${environment.backendProxy}/employee/togglestate`, {
      params: {
        tenantId : tenantRootId
      }
    })
  }

  getAllPermissions(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/role/fetch/permissions`);
  }

  createRole(body, tenantRootId): Observable<any>{
    return this.http.post<any>(`${environment.backendProxy}/role/create`, body, {
      params: {
        tenantId : tenantRootId
      }
    })
  }
  
}
