import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  constructor(private http: HttpClient) { }

  getAllEmailTemplateNames(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/email/templatenames`);
  }

  downloadTemplate(templateName: string, service: string): Observable<any> {
    if(service == "TM"){
      return this.http.get(`${environment.backendProxy}/admin/email/download/${templateName}`,
      {responseType:'blob', observe: 'response'});
    }
    else{//} if(service == "PM"){
      return this.http.get(`${environment.productProxy}/admin/email/download/${templateName}`,
      {responseType:'blob', observe: 'response'});
    }
  }

  uploadTemplate(file: File, name: string, service: string): Observable<any> {
    const formData = new FormData();
    formData.append("file", file, file.name);
    formData.append("name", name);
    if(service == "TM"){
      return this.http.post<any>(`${environment.backendProxy}/admin/email/template`, formData)
    }
    else{//} if(service == "PM"){
      return this.http.post<any>(`${environment.productProxy}/admin/email/template`, formData)
    }
  }

  getAllProductEmailTemplateNames(): Observable<any> {
    return this.http.get(`${environment.productProxy}/admin/email/templatenames`);
  }

}
