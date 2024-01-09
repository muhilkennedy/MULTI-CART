import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';
import { SupportedLanguages } from 'src/app/i18n/i18n.module';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user!: any;

  languages: any;
  selectedLanguage: any;
  secondaryemail!: string;

  constructor(private userService: UserService, private spinner: SpinnerService,
    private notification: NotificationService, public translate: TranslateService,
    private cookieService: CookieService) {
    this.languages = SupportedLanguages.languages;
  }

  ngOnInit(): void {
    // this.spinner.show();
    this.user = this.userService.getCurrentUserResponse();
    if (CommonUtil.isNullOrEmptyOrUndefined(this.user)) {
      // for testing only, will not be invoked in prod unless hard refresh happens
      this.userService.pingUser()
        .subscribe({
          next: (resp: any) => {
            this.user = resp.data;
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          }
        })
    }
  }

  getProfilePicture() {
    if (CommonUtil.isNullOrEmptyOrUndefined(this.user.employeeInfo) || CommonUtil.isNullOrEmptyOrUndefined(this.user.employeeInfo.profilepic)) {
      return "../../../assets/img/avatars/profile.png";
    }
    else {
      return this.user.employeeInfo.profilepic;
    }
  }

  onPictureSeclected(event: any) {
    this.spinner.show();
    let picture: File = event.target.files[0];
    if (CommonUtil.isNullOrEmptyOrUndefined(picture)) {
      return;
    }
    this.userService.uploadProfilePic(picture)
      .subscribe({
        next: (resp: any) => {
          this.user = resp.data;
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          this.spinner.hide();
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

  changeLanguage(langCode: string) {
    this.translate.use(langCode);
    this.cookieService.set("lang", langCode);
    window.location.reload();
  }

  onSecondartMailUpdate() {
    this.spinner.show();
    this.userService.updateSecondaryEmail(this.secondaryemail)
      .subscribe({
        next: (resp: any) => {
          this.user = resp.data;
          this.notification.fireAndForget({message : "Secondary mail updated"}, NotificationType.INFO);
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          this.spinner.hide();
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

  isMailValid() {
    if (CommonUtil.isNullOrEmptyOrUndefined(this.secondaryemail)) {
      return false;
    }
    return true;
  }

}
