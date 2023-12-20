import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

import { IconSetService } from '@coreui/icons-angular';
import { iconSubset } from './icons/icon-subset';
import { Title } from '@angular/platform-browser';
import { environment } from '../environments/environment';
import { IdleService } from '../app/service/util/idle.service';
// import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  template: `
  <app-notification></app-notification>
  <router-outlet></router-outlet>`,
})
export class AppComponent implements OnInit {
  title = environment.tenant;

  constructor(
    private router: Router,
    private titleService: Title,
    private iconSetService: IconSetService,
    private idleService: IdleService,
    // private fireMessaging: AngularFireMessaging,
    private http: HttpClient
  ) {
    this.titleService.setTitle(this.title);
    this.iconSetService.icons = { ...iconSubset };
    //TODO: validate inactive user and sign out/show popup. for active users refresh the token!
    this.idleService.idle$.subscribe(s => console.log('idle for 5mins'));
    this.idleService.wake$.subscribe(s => console.log('user action identified!'));
  }

  ngOnInit(): void {

    // this.fireMessaging.requestToken.subscribe(token => {
    
    //   console.log(token);
    //   this.http.post(`${environment.backendProxy}/admin/base/notification`, {
    //     target: token,
    //     title: 'hello world',
    //     message: 'First notification, kinda nervous',
    //   }).subscribe((resp) => { console.log(resp) });
   
    //   this.http.post(`${environment.backendProxy}/admin/base/topic/subscription`, {
    //     topic: 'weather',
    //     subscriber: token
    //   }).subscribe((resp) => { console.log(resp) });
   
    // }, error => {
   
    //   alert(error);
   
    // });

    // this.fireMessaging.onMessage((payload) => {
    //   // Get the data about the notification
    //   let notification = payload.notification;
    //   // Create a Message object and add it to the array
    //   alert({title: notification.title, body: notification.body, iconUrl: notification.icon});
    //  });

    

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
    });
  }
}
