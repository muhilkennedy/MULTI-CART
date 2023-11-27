import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

import { ClassToggleService, HeaderComponent } from '@coreui/angular';
import { TranslateService } from '@ngx-translate/core';
import { SupportedLanguages } from 'src/app/i18n/i18n.module';
import { SubscriberService } from '../../../service/Subscriber/subscriber.service';
import { CookieService } from 'ngx-cookie-service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { Router } from '@angular/router';
import { UserService } from 'src/app/service/user/user.service';
import { TenantService } from 'src/app/service/Tenant/tenant.service';

@Component({
  selector: 'app-default-header',
  templateUrl: './default-header.component.html',
})
export class DefaultHeaderComponent extends HeaderComponent implements OnInit {

  @Input() sidebarId: string = "sidebar";

  public mailInbox!: string;
  public newMessages = new Array(4)
  public newTasks = new Array(5)
  public newNotifications = new Array(5)

  constructor(private router: Router, private tenantService: TenantService,
              private cookieService: CookieService, private userService: UserService) {
    super();
  }

  ngOnInit(): void {
    this.tenantService.getMailInboxUrl()
        .subscribe({
          next: (resp : any) => {
            this.mailInbox = resp.data;
          },
          error: (err: any) => {
            console.log("failed to load inbox url");
          }
        })
  }

  logoutAction(){
    this.cookieService.delete(CommonUtil.TOKEN_KEY);
    this.router.navigate(['']);
  }

  getProfilePicture(){
    if(CommonUtil.isNullOrEmptyOrUndefined(this.userService.getCurrentUserResponse()) 
    || CommonUtil.isNullOrEmptyOrUndefined(this.userService.getCurrentUserResponse().employeeInfo)
    || CommonUtil.isNullOrEmptyOrUndefined(this.userService.getCurrentUserResponse().employeeInfo.profilepic)){
      return "./assets/img/avatars/profile.png";
    }
    return this.userService.getCurrentUserResponse().employeeInfo.profilepic;
  }

}
