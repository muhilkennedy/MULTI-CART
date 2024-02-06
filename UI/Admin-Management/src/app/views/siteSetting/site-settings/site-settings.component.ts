import { Component, OnInit } from '@angular/core';
import { Tenant } from 'src/app/model/tenant.model';
import { TenantService } from 'src/app/service/Tenant/tenant.service';
import { SiteSettingsService } from 'src/app/service/site-settings/site-settings.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-site-settings',
  templateUrl: './site-settings.component.html',
  styleUrls: ['./site-settings.component.scss']
})
export class SiteSettingsComponent implements OnInit {

  logoFile!: File;

  emailConfigKeys: any[] = new Array();
  emailConfigFields: any[] = new Array();
  emailConfigType: any;

  storageGcpConfigKeys: any[] = new Array();
  gcpConfigFields: any[] = new Array();
  gcpConfigType: any;

  smsConfigKeys: any[] = new Array();
  smsConfigFields: any[] = new Array();
  smsConfigType: any;

  emailSpinner = false;
  storageSpinner = false;
  smsSpinner = false;

  constructor(private siteService: SiteSettingsService, private spinner: SpinnerService,
    private notification: NotificationService, public tenantService: TenantService) {

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
            this.gcpConfigFields = new Array(this.storageGcpConfigKeys.length);
          },
          error: (err: any) => {

          },
          complete: () => {
            this.storageSpinner = false;
          }
        }
      );
    this.smsSpinner = true;
    this.siteService.getAllSMSConfigurationKeys()
      .subscribe(
        {
          next: (resp: any) => {
            this.smsConfigType = resp.data;
            this.smsConfigKeys = resp.dataList;
            this.smsConfigFields = new Array(this.smsConfigKeys.length);
          },
          error: (err: any) => {

          },
          complete: () => {
            this.smsSpinner = false;
          }
        }
      );
  }

  getTenantLogo() {
    return CommonUtil.isNullOrEmptyOrUndefined(this.tenantService.getCurrentTenant().details.details.logoUrl) ? "../../../../assets/img/tenant/logo.png" : this.tenantService.getCurrentTenant().details.details.logoUrl;
  }

  onFileSelected(event: any) {
    this.spinner.show();
    this.logoFile = event.target.files[0];
    if (CommonUtil.isNullOrEmptyOrUndefined(this.logoFile)) {
      return;
    }
    this.tenantService.uploadLogo(this.logoFile)
      .subscribe(
        {
          next: (resp: any) => {
            this.notification.fireAndWait({ message: "Tenant Logo updated!" }, NotificationType.INFO);
            this.tenantService.getCurrentTenant().details.details.logoUrl = resp.data.tenantDetail.details.logoUrl;
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          },
          complete: () => {
            this.spinner.hide();
          }
        }
      )
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
            this.notification.fireAndForget(CommonUtil.generateSimpleNotificationMessage("Email Configurations Updated"), NotificationType.WARNING);
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
            this.spinner.hide();
          },
          complete: () => {
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
            this.notification.fireAndWaitWarn({ message: "Email configurations has been updated!" });
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

  onGcsConfigSave() {
    //TODO: validate fields
    this.spinner.show();
    let configList = new Array();
    for (let index = 0; index < this.gcpConfigFields.length; index++) {
      let config = {
        type: this.gcpConfigType,
        key: this.storageGcpConfigKeys[index],
        value: this.gcpConfigFields[index]
      }
      configList.push(config);
    }
    this.siteService.addConfiguartions(configList)
      .subscribe(
        {
          next: (resp: any) => {
            this.notification.fireAndForget(CommonUtil.generateSimpleNotificationMessage("Storage Configuration Updated"), NotificationType.WARNING);
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
            this.spinner.hide();
          },
          complete: () =>{
            this.spinner.hide();
          }
        }
      );
  }

  onSmsConfigSave() {
    //TODO
  }

}
