import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/service/user/user.service';
import { TenantService } from 'src/app/service/Tenant/tenant.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import { CookieService } from 'ngx-cookie-service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';
import { map, tap } from 'rxjs';
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from 'src/environments/environment';

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

  showFPModal = false;

  constructor(tenantService: TenantService, private userService: UserService, private notification: NotificationService,
    private spinner: SpinnerService, private router: Router, private cookieService: CookieService,
    private socialAuthService: SocialAuthService, private sanitizer: DomSanitizer) {
    this.tenantName = tenantService.getCurrentTenant().tenantName;
    this.tenantTag = tenantService.getCurrentTenant().details.tagline;
    this.tenantLogo = tenantService.getCurrentTenant().details.details.logoUrl;
  }

  ngOnInit(): void {
    if (!CommonUtil.isNullOrEmptyOrUndefined(this.cookieService.get(CommonUtil.TOKEN_KEY))) {
      this.spinner.show();
      this.userService.pingUser()
        .subscribe(
          {
            next: (resp: any) => {
              this.userService.getCurrentUser().userEmail = resp.data.emailid;
              this.userService.getCurrentUser().userId = resp.data.rootid;
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
    else{
      this.socialAuthService.authState.subscribe({
        next: (resp) => {
          //gets response on successfull google login - SocialUser
          //console.log(resp);
          //we can get access token to connnect to other google api services
          //this.socialAuthService.getAccessToken(GoogleLoginProvider.PROVIDER_ID).then(accessToken => console.log(accessToken));
          this.spinner.show();
          this.userService.googleLogin(resp)
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
                  this.spinner.hide();
                  this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(error));
                },
                complete: () => {
                  this.spinner.hide();
                }
              }
            )
        },
        error: (err) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        }
      })
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
            this.notification.fireAndForget({ message: 'Invalid Credentials! Please Login Again!' }, NotificationType.DANGER);
            this.spinner.hide();
          },
          complete: () => {
            this.spinner.hide();
          }
        }
      )
  }

  toggleFPModal() {
    this.showFPModal = !this.showFPModal;
  }

  //done to avoid reopening issue on click outside modal area.
  handleFPModal(event: boolean) {
    this.showFPModal = event;
  }

  loadFPModal() {
    this.toggleFPModal();
  }

  initiateReset() {
    this.toggleFPModal();
    this.spinner.show();
    this.userService.initiatePasswordReset(this.username)
      .subscribe({
        next: (resp: any) => {
          this.notification.fireAndForget({ message: "Password Reset Link Sent Successfully To Registered Email!" }, NotificationType.PRIMARY);
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

  loginWithGoogle(): void {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID)
      .then((resp) => {
        console.log(resp);
      });
    this.socialAuthService.authState.subscribe({
      next: (resp) => {
        console.log(resp);
      },
      error: (err) => {
        console.log(err);
      }
    })


  }

}
