import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WidgetService {

  private _pendingTaskCount: number = 0;
  private _notificationsCount: number = 0;

  set pendingTaskCount(count: number){
    this._pendingTaskCount = count;
  }

  get pendingTaskCount(){
    return this._pendingTaskCount;
  }

  set notificationsCount(count: number){
    this._notificationsCount = count;
  }

  get notificationsCount(){
    return this._notificationsCount;
  }

  constructor() { }
}
