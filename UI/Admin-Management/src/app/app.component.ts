import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

import { IconSetService } from '@coreui/icons-angular';
import { iconSubset } from './icons/icon-subset';
import { Title } from '@angular/platform-browser';
import { environment } from '../environments/environment';
import { IdleService } from '../app/service/util/idle.service';

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
    private idleService: IdleService
  ) {
    titleService.setTitle(this.title);
    iconSetService.icons = { ...iconSubset };
    //TODO: validate inactive user and sign out/show popup. for active users refresh the token!
    idleService.idle$.subscribe(s => console.log('idle for 5mins'));
    idleService.wake$.subscribe(s => console.log('user action identified!'));
  }

  ngOnInit(): void {
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
    });
  }
}
