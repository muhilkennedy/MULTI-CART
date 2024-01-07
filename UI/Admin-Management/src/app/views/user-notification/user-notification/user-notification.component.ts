import { Component, OnInit } from '@angular/core';
import { UserNotficationService } from '../../../service/user-notification/user-notfication.service'
import { EventSourcePolyfill } from 'ng-event-source';
import { NotificationType } from 'src/app/service/util/notification.service';
import { Router } from '@angular/router';
import { WidgetService } from 'src/app/service/widget/widget.service';

export class UserNotification{

  private _title!: string;
  private _content!: string;
  private _type!: number;
  private _redirectUrl!: string;
  private _active!: boolean;
  private _rootId!: number;

  set title(title:string){
    this._title = title;
  }

  get title():string{
    return this._title;
  }

  set content(content:string){
    this._content = content;
  }

  get content():string{
    return this._content;
  }

  set type(type:number){
    this._type = type;
  }

  get type():number{
    return this._type;
  }

  set redirectUrl(redirectUrl:string){
    this._redirectUrl = redirectUrl;
  }

  get redirectUrl():string{
    return this._redirectUrl;
  }
  
  set active(active:boolean){
    this._active = active;
  }

  get active():boolean{
    return this._active;
  }

  set rootId(rootId:number){
    this._rootId = rootId;
  }

  get rootId():number{
    return this._rootId;
  }
}

@Component({
  selector: 'app-user-notification',
  templateUrl: './user-notification.component.html',
  styleUrls: ['./user-notification.component.scss']
})
export class UserNotificationComponent implements OnInit {

  notifications: UserNotification[] = new Array();

  constructor(private notificationService: UserNotficationService, private router: Router, private widgetService: WidgetService) {

  }

  ngOnInit(): void {
    let eventSource: EventSourcePolyfill = this.notificationService.getAllUnreadNotifications();
    eventSource.onmessage = (message => {
      let notification = JSON.parse(message.data);
      let usrNotification: UserNotification = new UserNotification();
      usrNotification.title = notification.title;
      usrNotification.content = notification.content;
      usrNotification.type = notification.type;
      notification.redirectUrl = notification.redirectpath;
      notification.rootId = notification.rootid;
      this.notifications.push(notification);
    });
    eventSource.onopen = () => {
      // No-Op
    };
    eventSource.onerror = (e: any) => {
      eventSource.close();
    }
  }

  msgRead(notification: UserNotification){
    this.notificationService.markAsRead(notification.rootId)
    .subscribe({
      next: (resp: any) => {
        let index = this.notifications.indexOf(notification);
        this.notifications.splice(index, 1);
        --this.widgetService.notificationsCount;
      },
      error: (err: any) => {
        alert("Failed to close notification");
      }
    })
  }

  navigate(notification: UserNotification){
    this.router.navigate([ notification.redirectUrl ]);
  }

  getType(notification: UserNotification){
    switch(notification.type){
      case 0 : return NotificationType.PRIMARY;
      case 1 : return NotificationType.DANGER;
      case 2 : return NotificationType.WARNING;
    }
    return NotificationType.INFO;
  }

}
