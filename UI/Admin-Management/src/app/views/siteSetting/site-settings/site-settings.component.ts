import { Component, OnInit } from '@angular/core';
import { SiteSettingsService } from 'src/app/service/site-settings/site-settings.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-site-settings',
  templateUrl: './site-settings.component.html',
  styleUrls: ['./site-settings.component.scss']
})
export class SiteSettingsComponent implements OnInit {

  emailConfigKeys: any[] = new Array();
  emailConfigFields: any[] = new Array();
  emailConfigType: any;
  storageGcpConfigKeys: any[] = new Array();
  gcpConfigFields: any[] = new Array();
  gcpConfigType: any;

  emailSpinner = false;
  storageSpinner = false;

  constructor(private siteService: SiteSettingsService, private spinner: SpinnerService, private notification: NotificationService) {

  }

  ngOnInit(): void {
    this.emailSpinner = true;
    this.siteService.getAllEmailConfigurationKeys()
      .subscribe(
        {
          next: (resp: any) => {
            this.emailConfigType = resp.data;
            this.emailConfigKeys = resp.dataList;
            this.emailConfigFields = new Array(this.emailConfigKeys.length);
          },
          error: (err: any) => {

          },
          complete: () => {
            this.emailSpinner = false;
          }
        }
      );
    this.storageSpinner = true;
    this.siteService.getAllGCPConfigurationKeys()
      .subscribe(
        {
          next: (resp: any) => {
            this.gcpConfigType = resp.data;
            this.storageGcpConfigKeys = resp.dataList;
            this.gcpConfigFields = new Array(this.emailConfigKeys.length);
          },
          error: (err: any) => {

          },
          complete: () => {
            this.storageSpinner = false;
          }
        }
      );
  }

  onEmailConfigSave() {
    //TODO: validate fields
    this.spinner.show();
    let configList = new Array();
    for (let index = 0; index < this.emailConfigKeys.length; index++) {
      let config = {
        type: this.emailConfigType,
        key: this.emailConfigKeys[index],
        value: this.emailConfigFields[index]
      }
      configList.push(config);
    }
    this.siteService.addConfiguartions(configList)
      .subscribe(
        {
          next: (resp: any) => {
            this.loadEmailConfig();
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
            this.spinner.hide();
          }
        }
      );
  }

  loadEmailConfig() {
    this.siteService.loadNewEmailConfig()
      .subscribe(
        {
          next: (resp: any) => {
            this.notification.fireAndWaitWarn({ message : "Email configurations has been updated!"});
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          },
          complete: () => {
            this.spinner.hide();
          }
        }
      );
  }

}
