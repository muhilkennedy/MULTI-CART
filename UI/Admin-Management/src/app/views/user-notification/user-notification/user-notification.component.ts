import { Component, OnInit } from '@angular/core';
import { UserNotficationService } from '../../../service/user-notification/user-notfication.service'
import { EventSourcePolyfill } from 'ng-event-source';

@Component({
  selector: 'app-user-notification',
  templateUrl: './user-notification.component.html',
  styleUrls: ['./user-notification.component.scss']
})
export class UserNotificationComponent implements OnInit {

  notifications: any[] = new Array();

  constructor(private notificationService: UserNotficationService) {

  }

  ngOnInit(): void {
    let eventSource: EventSourcePolyfill = this.notificationService.getAllUnreadNotifications();
    eventSource.onmessage = (message => {
      this.notifications.push(JSON.parse(message.data));
    });
    eventSource.onopen = () => {
      // No-Op
    };
    eventSource.onerror = (e: any) => {
      eventSource.close();
    }
  }

  msgRead(notification: any){
    this.notificationService.markAsRead(notification.rootId)
    .subscribe({
      next: (resp: any) => {
        let index = this.notifications.indexOf(notification);
        this.notifications.splice(index, 1);
      },
      error: (err: any) => {
        alert("Failed to close notification");
      }
    })
  }

}
