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

  downloadTemplate(templateName: string): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/email/download/${templateName}`,
    {responseType:'blob', observe: 'response'});
  }

  uploadTemplate(file: File, name: string): Observable<any> {
    const formData = new FormData();
    formData.append("file", file, file.name);
    formData.append("name", name);
    return this.http.post<any>(`${environment.backendProxy}/admin/email/template`, formData)
  }

}
