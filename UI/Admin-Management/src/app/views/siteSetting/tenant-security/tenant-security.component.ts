import { Component, OnInit } from '@angular/core';
import { SiteSettingsService } from 'src/app/service/site-settings/site-settings.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-tenant-security',
  templateUrl: './tenant-security.component.html',
  styleUrls: ['./tenant-security.component.scss']
})
export class TenantSecurityComponent implements OnInit {

  googleOauthConfigKeys: any[] = new Array();
  googleOauthConfigFields: any[] = new Array();
  googleOauthConfigType: any;

  constructor(private siteService: SiteSettingsService, private spinner: SpinnerService,
    private notification: NotificationService) {

  }

  ngOnInit(): void {
    this.siteService.getAllGoogleOauthConfigKeys()
      .subscribe({
        next: (resp: any) => {
          this.googleOauthConfigKeys = resp.dataList;
          this.googleOauthConfigType = resp.data;
          this.googleOauthConfigFields = new Array(this.googleOauthConfigKeys.length);
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

  onGoogleOauthUpdate() {
    this.spinner.show();
    let configList = new Array();
    for (let index = 0; index < this.googleOauthConfigKeys.length; index++) {
      let config = {
        type: this.googleOauthConfigType,
        key: this.googleOauthConfigKeys[index],
        value: this.googleOauthConfigFields[index]
      }
      configList.push(config);
    }
    this.siteService.addConfiguartions(configList)
      .subscribe(
        {
          next: (resp: any) => {
            this.notification.fireAndWait({ message: "Oauth settings updates" }, NotificationType.WARNING);
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
            this.spinner.hide();
          },
          complete: ()=>{
            this.spinner.hide();
          }
        }
      );
  }

}
