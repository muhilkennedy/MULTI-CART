import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UserService } from 'src/app/service/user/user.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: 'dashboard.component.html',
  styleUrls: ['dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  
  audio = new Audio();
  
  constructor(public translate: TranslateService, private userService: UserService, 
            private router: Router, private notification: NotificationService,
            private fireMessaging: AngularFireMessaging,
            private http: HttpClient) {
      this.audio.src = "../../../assets/sounds/notify.mp3";
      this.audio.load();
  }

  ngOnInit(): void {

    this.fireMessaging.requestPermission.subscribe({
      next: (token: any) => {
        //No-OP
      },
      error: (error) => {
        console.log("Notification Permission Ignored");
      }
    });
    
    this.fireMessaging.requestToken.subscribe({
      next: (token: any) => {
        this.http.post(`${environment.backendProxy}/notification/push/topic/subscription`, {
          topic: 'ADMINUSER',
          subscriber: token
        }).subscribe((resp) => { 
          //No-OP
         });
      },
      error: (error) => {
        console.log("Notification load error : " + error);
      }
    });
  
    this.fireMessaging.onMessage((payload) => {
      // Get the data about the notification
      let notification = payload.notification;
      // Create a Message object and add it to the array
      this.notification.fireAndForget({title: notification.title, message : notification.body}, NotificationType.PRIMARY);
      this.audio.play();
     });
  }

  showTaskWidget(){
    return this.userService.getCurrentUserResponse().employeeInfo.details.showTasks;
  }

  showNotificationWidget(){
    return this.userService.getCurrentUserResponse().employeeInfo.details.showNotifications;
  }

  showCalendarWidget(){
    return this.userService.getCurrentUserResponse().employeeInfo.details.showCalendar;
  }

}
