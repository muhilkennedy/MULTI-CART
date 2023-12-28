import { Component, OnInit } from '@angular/core';
import { TenantService } from 'src/app/service/Tenant/tenant.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-tenant-info',
  templateUrl: './tenant-info.component.html',
  styleUrls: ['./tenant-info.component.scss']
})
export class TenantInfoComponent implements OnInit {

  tenantName: string;
  tenantId: string;
  tagline: string;
  street: string;
  city: string;
  pincode: string;
  contact: string;
  email: string;
  fssai: string;
  gmap: string;
  gstin: string;

  ngOnInit(): void {
  }

  constructor(public tenantService: TenantService, private notification: NotificationService, private spinner: SpinnerService){
    this.tenantName = this.tenantService.getCurrentTenant().tenantName;
    this.tenantId = this.tenantService.getCurrentTenant().tenantId;
    this.tagline = this.tenantService.getCurrentTenant().details.tagline;
    this.street = this.tenantService.getCurrentTenant().details.street;
    this.city = this.tenantService.getCurrentTenant().details.city;
    this.pincode = this.tenantService.getCurrentTenant().details.pincode;
    this.email = this.tenantService.getCurrentTenant().details.emailid;
    this.contact = this.tenantService.getCurrentTenant().details.contact;
    this.fssai = tenantService.getCurrentTenant().details.details.fssai;
    this.gstin = tenantService.getCurrentTenant().details.details.gstin;
    this.gmap = tenantService.getCurrentTenant().details.details.gmapUrl;
  }

  onTenantNameUpdate(){
    this.tenantService.updateValue("name", this.tenantName).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant Name Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().tenantName = this.tenantName;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

  onTenantEmailUpdate(){
    this.tenantService.updateValue("email", this.email).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant Email Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().details.emailid = this.email;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

  onTenantContactUpdate(){
    this.tenantService.updateValue("contact", this.contact).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant Contact Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().details.contact = this.contact;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

  onTenantTaglineUpdate(){
    this.tenantService.updateValue("tagline", this.tagline).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant TagLine Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().details.tagline = this.tagline;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

  onTenantStreetUpdate(){
    this.tenantService.updateValue("street", this.street).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant Street Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().details.street = this.street;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

  onTenantCityUpdate(){
    this.tenantService.updateValue("city", this.city).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant Name Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().details.city = this.city;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

  onTenantPincodeUpdate(){
    this.tenantService.updateValue("pincode", this.pincode).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant Pincode Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().details.pincode = this.pincode;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

  onTenantGstinUpdate(){
    this.tenantService.updateValue("gstin", this.gstin).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant Name Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().details.details.fssai = this.gstin;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

  onTenantFssaiUpdate(){
    this.tenantService.updateValue("fssai", this.fssai).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant Fssai Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().details.details.fssai = this.fssai;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

  onTenantGmapUpdate(){
    this.tenantService.updateValue("gmap", this.gmap).subscribe({
      next: (resp: any) => {
        this.notification.fireAndForget({message: "Tenant Name Updated!"}, NotificationType.WARNING);
        this.tenantService.getCurrentTenant().details.details.gmapUrl = this.gmap;
      },
      error: (error: any) => {
        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
      }
    })
  }

}
