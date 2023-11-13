import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { TenantService } from 'src/app/service/Tenant/tenant.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-onboard',
  templateUrl: './onboard.component.html',
  styleUrls: ['./onboard.component.scss']
})
export class OnboardComponent implements OnInit {

  public tenantName: string;
  public uniqueName: string;
  public tagLine: string;
  public tenantEmail: string;
  public tenantContact: string;

  public renewalDate;
  public expiryDate;
  public datePipe: DatePipe;

  range: FormGroup = new FormGroup({
    start: new FormControl(new Date()),
    end: new FormControl(this.addOneYear()),
  });

  addOneYear() {
    let dateCopy = new Date();
    dateCopy.setFullYear(dateCopy.getFullYear() + 1);
    return dateCopy;
  }

  constructor(private tenantService: TenantService, private toastr: ToastrService,
    private route: Router, private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('en-GB');
    this.datePipe = new DatePipe('en-GB');
  }

  ngOnInit(): void {
  }

  onSave() {
    const body = {
      tenantName: this.tenantName,
      uniqueName: this.uniqueName,
      tagLine: this.tagLine,
      email: this.tenantEmail,
      contact: this.tenantContact,
      details: {

      },
      startDate: this.datePipe.transform(this.range.value.start, 'yyyy-MM-dd'),
      endDate: this.datePipe.transform(this.range.value.end, 'yyyy-MM-dd')
    };

    this.tenantService.onboardTenant(body)
      .subscribe(
        {
          next: (resp: any) => {
            this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Tenant onboarded successfully</b>.', '', {
              disableTimeOut: false,
              closeButton: true,
              enableHtml: true,
              toastClass: "alert alert-success alert-with-icon",
              positionClass: 'toast-' + 'bottom' + '-' + 'center'
            });
            this.route.navigate(['/dashboard']);
          },
          error: (error) => {
            console.log("error in loading tenant");
            this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error Adding tenant : ' + error.error.message + '</b>.', '', {
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

}
