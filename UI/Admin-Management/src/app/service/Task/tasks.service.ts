import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  constructor(private http: HttpClient) { }

  createTask(body: any): Observable<any>{
    return this.http.post<any>(`${environment.backendProxy}/task`, body);
  }

  getTasks(pageSize: number, pageNumber: number, type: string, status: string, broadCasted: boolean): Observable<any>{
    return this.http.get(`${environment.backendProxy}/task`, {
      params: {
        type: type,
        status: status,
        broadCasted : broadCasted,
        pageSize: pageSize,
        pageNumber: pageNumber
      }
    });
  }

  getTasksCount(status: string): Observable<any>{
    return this.http.get(`${environment.backendProxy}/task/${status}/count`);
  }

}
