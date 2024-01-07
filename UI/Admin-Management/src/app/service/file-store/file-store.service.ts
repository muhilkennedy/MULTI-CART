import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FileStoreService {

  constructor(private http: HttpClient) { }

  uploadFile(internalFile: boolean, file: File) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("internalFile", internalFile + '');
    return this.http.post<any>(`${environment.backendProxy}/admin/file/upload`, formData)
  }

  getAllFiles(pageNumber: number, pageSize: number): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/file`, {
      params: {
        pageNumber: pageNumber,
        pageSize: pageSize
      }
    });
  }

  getTotalUtilizedLimit(): Observable<any> {
    return this.http.get(`${environment.backendProxy}/admin/file/utilizedlimit`);
  }

  deleteFile(fileId: number) {
    return this.http.delete<any>(`${environment.backendProxy}/admin/file`, {
      params: {
        fileId: fileId
      }
    });
  }

  downloadFile(fileId: number) {
    return this.http.get(`${environment.backendProxy}/admin/file/download/${fileId}`,
      { responseType: 'blob', observe: 'response' });
  }

}
