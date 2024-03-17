import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user.model';
import { environment } from 'src/environments/environment';
import { Permissions } from 'src/app/service/user/permission.service';
import { CommonUtil } from '../util/common-util.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private user: User;
  private userResponse: any;

  constructor(private http: HttpClient) {
    this.user = new User();
  }

  getCurrentUser(): User {
    return this.user;
  }

  getCurrentUserResponse(): any {
    return this.userResponse;
  }

  setCurrentUserResponse(userResponse: any){
    this.userResponse = userResponse;
  }

  login(body: any): Observable<any> {
    return this.http.post<any>(`${environment.backendProxy}/user/employee/login`, body, { observe: 'response' })
  }

  googleLogin(body: any): Observable<any> {
    return this.http.post<any>(`${environment.backendProxy}/social/login/google`, body, { observe: 'response' })
  }

  pingUser(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/employee/ping`);
  }

  createEmployee(body: any): Observable<any> {
    return this.http.post<any>(`${environment.backendProxy}/employee`, body)
  }

  getAllEmployees(pageNumber: number, pageSize: number, sortBy: string, sortOrder: string): Observable<any> {
    return this.http.get(`${environment.backendProxy}/employee`, {
      params: {
        pageNumber: pageNumber,
        pageSize: pageSize,
        sortBy: sortBy,
        sortOrder: sortOrder
      }
    });
  }

  searchEmployeesByNameOrEmail(searchText: string, limit: number): Observable<any> {
    return this.http.get(`${environment.backendProxy}/employee/search`, {
      params: {
        key: searchText,
        limit: limit
      }
    });
  }

  updateEmployeeProof(uniqueName: string, proofDoc: File): Observable<any> {
    const formData = new FormData();
    formData.append("uniqueName", uniqueName);
    formData.append("document", proofDoc, proofDoc.name);
    return this.http.post<any>(`${environment.backendProxy}/employee/proof`, formData)
  }

  getAllMatchingEmployeesForName(empName: string): Observable<any> {
    return this.http.get(`${environment.backendProxy}/employee/typeahead`, {
      params: {
        name: empName
      }
    });
  }

  getAllPermissions(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/role/fetch/permissions`);
  }

  getAllRoles(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/role/fetch`);
  }

  updateEmployeeRole(roleId: number, userId: number, status: boolean): Observable<any> {
    return this.http.put(`${environment.backendProxy}/employee/role/${roleId}`, {}, {
      params:{
        userId: userId,
        status: status
      }
    });
  }

  getAllEmployeeRoles(id: any): Observable<any> {
    return this.http.get(`${environment.backendProxy}/employee/${id}/roles`);
  }

  createRole(body: any): Observable<any> {
    return this.http.post<any>(`${environment.backendProxy}/role/create`, body)
  }

  toggleRoleStatus(roleId: number): Observable<any> {
    return this.http.put<any>(`${environment.backendProxy}/role/toggle`, null, {
      params: {
        id: roleId
      }
    })
  }

  toggleEmployeeStatus(userId: number): Observable<any> {
    return this.http.put<any>(`${environment.backendProxy}/employee/togglestate`, null, {
      params: {
        userId: userId
      }
    })
  }

  initiatePasswordReset(emailId: any): Observable<any>{
    return this.http.post<any>(`${environment.backendProxy}/user/employee/password/reset/initiate`, null, {
      params: {
        emailId: emailId
      }
    });
  }

  resetPassword(body: any): Observable<any>{
    return this.http.post<any>(`${environment.backendProxy}/user/employee/password/reset`, body);
  }

  uploadProfilePic(file: File): Observable<any> {
    const formData = new FormData();
    formData.append("picture", file, file.name);
    return this.http.post<any>(`${environment.backendProxy}/employee/profilepic`, formData)
  }


  updateSecondaryEmail(email: string): Observable<any> {
    return this.http.put<any>(`${environment.backendProxy}/employee/secondaryemail`, {
      emailId : email
    })
  }

  updateUserLocale(locale: string): Observable<any> {
    return this.http.put<any>(`${environment.backendProxy}/employee/locale/${locale}`, {});
  }

  getUserPermissions(): any[]{
    if(CommonUtil.isNullOrEmptyOrUndefined(this.getCurrentUserResponse())){
      return new Array();
    }
    return this.getCurrentUserResponse().userPermissions;
  }

  doesUserHavePermission(permission: Permissions): boolean{
    if(CommonUtil.isNullOrEmptyOrUndefined(this.getUserPermissions())){
      return false;
    }
    let userPerm = this.getUserPermissions().filter(perm => permission.toString() == perm);
    return userPerm.length > 0 ;
  }

  findEmployeeByUniqueName(uniqueName: string): Observable<any> {
    return this.http.get(`${environment.backendProxy}/employee/fetch`,{
      params: {
        uniqueName: uniqueName
      }
    });
  }

  updateWidgetPreference(widget: string, status: boolean): Observable<any> {
    return this.http.put<any>(`${environment.backendProxy}/employee/preference/widget/${widget}`, null, {
      params: {
        status: status
      }
    })
  }

}
