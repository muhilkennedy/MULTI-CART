import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/service/user/user.service';
import { TenantService } from 'src/app/service/Tenant/tenant.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import { CookieService } from 'ngx-cookie-service';
import { CommonUtil } from 'src/app/service/util/common-util.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  tenantName: string;
  tenantTag: string;
  tenantLogo: string;

  username?: string;
  password?: string;

  constructor(tenantService: TenantService, private userService: UserService,
              private spinner: SpinnerService, private router: Router, private cookieService: CookieService) {
    this.tenantName = tenantService.getCurrentTenant().tenantName;
    this.tenantTag = tenantService.getCurrentTenant().details.tagline;
    this.tenantLogo = tenantService.getCurrentTenant().details.details.logoUrl;
  }

  ngOnInit(): void {
    if(!CommonUtil.isNullOrEmptyOrUndefined(this.cookieService.get(CommonUtil.TOKEN_KEY))){
      this.spinner.show();
      this.userService.pingUser()
        .subscribe(
          {
            next: (resp: any) => {
              this.userService.getCurrentUser().userEmail = resp.data.emailid;
              this.userService.getCurrentUser().userId = resp.data.rootId;
              this.userService.getCurrentUser().userName = resp.data.fname + resp.data.lname;
              this.router.navigate(['/dashboard']);
            },
            error: (error: any) => {
              this.spinner.hide();
              this.router.navigate(['/login']);
            },
            complete: () => {
              this.spinner.hide();
            }
          }
        )
    }
  }

  loginAction(): void {
    let body = {
      emailId: this.username,
      uniqueName: this.username,
      password: this.password
    }
    this.spinner.show();
    this.userService.login(body)
      .subscribe(
        {
          next: (resp: any) => {
            this.userService.getCurrentUser().userEmail = resp.body.data.emailid;
            this.userService.getCurrentUser().userId = resp.body.data.rootId;
            this.userService.getCurrentUser().userName = resp.body.data.fname + resp.body.data.lname;
            this.userService.getCurrentUser().token = resp.headers.get(CommonUtil.TOKEN_KEY);;
            this.cookieService.set(CommonUtil.TOKEN_KEY, this.userService.getCurrentUser().token);
            this.router.navigate(['/dashboard']);
          },
          error: (error: any) => {
            this.router.navigate(['/login', { message : 'Invalid Credentials! Please Login Again!'}]);
          },
          complete: () => {
            this.spinner.hide();
          }
        }
      )
  }

}
