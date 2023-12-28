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
import { TasksService } from 'src/app/service/Task/tasks.service';
import { WidgetService } from 'src/app/service/widget/widget.service';
import { UserNotficationService } from 'src/app/service/user-notification/user-notfication.service';

@Component({
  selector: 'app-default-header',
  templateUrl: './default-header.component.html',
})
export class DefaultHeaderComponent extends HeaderComponent implements OnInit {

  @Input() sidebarId: string = "sidebar";

  taskCount!: number;
  notificationCount!: number;

  public mailInbox!: string;
  public newMessages = new Array(4)
  public newTasks = new Array(5)
  public newNotifications = new Array(5)

  constructor(private router: Router, private tenantService: TenantService, private taskService: TasksService,
    private cookieService: CookieService, private userService: UserService, private notificationService: UserNotficationService,
      private widgetService: WidgetService) {
    super();
  }

  ngOnInit(): void {
    this.tenantService.getMailInboxUrl()
      .subscribe({
        next: (resp: any) => {
          this.mailInbox = resp.data;
        },
        error: (err: any) => {
          console.log("failed to load inbox url");
        }
      })

    this.taskService.getTasksCount("Pending")
      .subscribe({
        next: (resp: any) => {
          this.taskCount = resp.data;
          this.widgetService.pendingTaskCount = this.taskCount;
        },
        error: (err: any) => {
          console.log("failed to load tasks count");
        }
      })

    this.notificationService.getUnreadNotificationsCount()
      .subscribe({
        next: (resp: any) => {
          this.notificationCount = resp.data;
          this.widgetService.notificationsCount = this.notificationCount;
        },
        error: (err: any) => {
          console.log("failed to load tasks count");
        }
      })
  }

  logoutAction() {
    this.cookieService.delete(CommonUtil.TOKEN_KEY);
    this.router.navigate(['']);
  }

  getProfilePicture() {
    if (CommonUtil.isNullOrEmptyOrUndefined(this.userService.getCurrentUserResponse())
      || CommonUtil.isNullOrEmptyOrUndefined(this.userService.getCurrentUserResponse().employeeInfo)
      || CommonUtil.isNullOrEmptyOrUndefined(this.userService.getCurrentUserResponse().employeeInfo.profilepic)) {
      return "./assets/img/avatars/profile.png";
    }
    return this.userService.getCurrentUserResponse().employeeInfo.profilepic;
  }

}
