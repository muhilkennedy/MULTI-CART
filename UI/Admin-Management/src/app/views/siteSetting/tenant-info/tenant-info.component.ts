import { Component, OnInit } from '@angular/core';
import { TenantService } from 'src/app/service/Tenant/tenant.service';

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

  constructor(public tenantService: TenantService){
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

  }

}
