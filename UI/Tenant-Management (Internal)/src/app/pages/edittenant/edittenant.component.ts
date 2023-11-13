import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { TenantService } from 'src/app/service/Tenant/tenant.service';
import { EventSourcePolyfill } from 'ng-event-source';
import { environment } from 'src/environments/environment';
import { DomSanitizer } from "@angular/platform-browser";
import { CommonUtil } from 'src/app/service/util/common-util.service';

@Component({
  selector: 'app-edittenant',
  templateUrl: './edittenant.component.html',
  styleUrls: ['./edittenant.component.scss']
})
export class EdittenantComponent implements OnInit {

  selectedRealm: any;
  selectedTenant: any;
  realms: any[];

  isRealmSelected = false;

  public renewalDate;
  public expiryDate;
  public datePipe: DatePipe;

  storage: string;
  bucket: string;
  storageConfig: string;

  subscriptions: any[] = new Array();
  currentValidity: string;

  file: File = null;

  range: FormGroup = new FormGroup({
    start: new FormControl(new Date()),
    end: new FormControl(this.addOneYear()),
  });

  addOneYear() {
    // Making a copy with the Date() constructor
    let dateCopy = new Date();
    dateCopy.setFullYear(dateCopy.getFullYear() + 1);
    return dateCopy;
  }

  constructor(private http: HttpClient,
    private toastr: ToastrService,
    private cookieService: CookieService,
    private  tenantService: TenantService,
    private sanitizer: DomSanitizer) {
    this.datePipe = new DatePipe('en-GB');
  }

  ngOnInit(): void {
    this.tenantService.getAllTenants()
      .subscribe({
        next: (resp: any) => {
          this.realms = resp.dataList;
        },
        error: (error) => {
          this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error Adding tenant : ' + error.error.message + '</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-error alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' + 'center'
          });
        }
      });
  }

  getRealmData(tenantId) {
    this.isRealmSelected = true;
    this.selectedTenant = this.getSelectedRealm();
    this.subscriptions.splice(0,this.subscriptions.length)
    //we need to call event stream api with header config separately.
    let eventSource = new EventSourcePolyfill(`${environment.backendProxy}/admin/tenant/subscriptions?tenantId=${tenantId}`, 
                        { 
                          headers: {
                            'X-Tenant': environment.tenantId,
                            'Authorization': `Bearer ${this.cookieService.get("X-Token")}`,
                            'Accept-Language': this.tenantService.getCurrentTenant().locale
                          } 
                        });
    eventSource.onmessage = (message => {
      this.subscriptions.push(JSON.parse(message.data));
    });
    eventSource.onopen = (a) => {
      // Do stuff here
    };
    eventSource.onerror = (e) => {
      eventSource.close();
    }
  }

  onRenew() {
    const body = {
      startDate: this.datePipe.transform(this.range.value.start, 'yyyy-MM-dd'),
      endDate: this.datePipe.transform(this.range.value.end, 'yyyy-MM-dd')
    };
    this.tenantService.renewSubscription(body, this.selectedRealm)
      .subscribe({
        next: (resp: any) => {
          this.subscriptions.push(resp.data);
          this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Tenant Renewal successful</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-success alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' + 'center'
          });
        },
        error: (error) => {
          this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error Renewing tenant : ' + error.error.message + '</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-error alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' + 'center'
          });
        }
      }
      );
  }

  selectFile(event){
    this.file = event.target.files[0]; 
  }

  onUpload(){
    this.tenantService.uploadLogo(this.file, this.selectedRealm)
        .subscribe(
          {
            next: (resp: any) => {
              this.selectedTenant = resp.data;
              this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Tenant Logo Updated</b>.', '', {
                disableTimeOut: false,
                closeButton: true,
                enableHtml: true,
                toastClass: "alert alert-success alert-with-icon",
                positionClass: 'toast-' + 'bottom' + '-' + 'center'
              });
            },
            error: (err: any) => {
              this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error Uploading tenant logo : ' + err.error.message + '</b>.', '', {
                disableTimeOut: false,
                closeButton: true,
                enableHtml: true,
                toastClass: "alert alert-error alert-with-icon",
                positionClass: 'toast-' + 'bottom' + '-' + 'center'
              });
            }
          }
        )
  }

  getLogo(){
    if(this.selectedTenant.tenantDetail.details.logoUrl == undefined || this.selectedTenant.tenantDetail.details.logoUrl == null){
      return "";
    }
    else{
      return this.sanitizer.bypassSecurityTrustResourceUrl(this.selectedTenant.tenantDetail.details.logoUrl);
    }
  }

  getSelectedRealm(){
    return this.realms.filter(realm => realm.rootId == this.selectedRealm).pop();
  }

  onConfigUpdate(){
    let body = {
      type : this.storage,
      config: this.storageConfig,
      bucket: this.bucket
    }
    this.tenantService.createStorageConfig(body, this.selectedRealm)
        .subscribe(
          {
            next: (resp: any) => {
              this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Tenant Storage updated</b>.', '', {
                disableTimeOut: false,
                closeButton: true,
                enableHtml: true,
                toastClass: "alert alert-success alert-with-icon",
                positionClass: 'toast-' + 'bottom' + '-' + 'center'
              });
            },
            error: (err: any) => {
              this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error updating tenant : ' + err.error.message + '</b>.', '', {
                disableTimeOut: false,
                closeButton: true,
                enableHtml: true,
                toastClass: "alert alert-error alert-with-icon",
                positionClass: 'toast-' + 'bottom' + '-' + 'center'
              });
            }
          }
    )
  }

}