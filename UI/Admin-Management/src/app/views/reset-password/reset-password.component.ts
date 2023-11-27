import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { get } from 'scriptjs';
import { TenantService } from 'src/app/service/Tenant/tenant.service';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import { environment } from 'src/environments/environment';

declare var grecaptcha: any;

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  tenantName: string;
  tenantTag: string;
  tenantLogo: string;

  newPassword!: string;
  confirmPassword!: string;

  isValidated: boolean = false;
  //regex for atleast one number/caps character/special character.
  passwordRegex = /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{10,20}$/;

  empUniqueName!: string;
  otp!: string;

  constructor(private userService: UserService, private tenantService: TenantService, private route: ActivatedRoute,
    private spinner: SpinnerService, private notification: NotificationService, private router: Router,
    private elementRef:ElementRef) {
    this.tenantName = tenantService.getCurrentTenant().tenantName;
    this.tenantTag = tenantService.getCurrentTenant().details.tagline;
    this.tenantLogo = tenantService.getCurrentTenant().details.details.logoUrl;
  }

  ngOnInit(): void {
    
    this.route.queryParams
      .subscribe((params: any) => {
        this.empUniqueName = params.uniqueName;
        this.otp = params.otp;
      }
      );
    //we need to load this dynamically to make sure captcha container is rendered along with js load.
    get(environment.recaptcha_url, () => {
      console.log("Recaptcha loaded");
    });
  }

  isPassworsCriteriaGood() {
    return this.passwordRegex.test(this.newPassword);
  }

  doesPasswordMatch() {
    if (CommonUtil.isNullOrEmptyOrUndefined(this.newPassword)) {
      return false;
    }
    return (this.newPassword == this.confirmPassword);
  }

  resetAction() {
    this.isValidated = true;
    const reCaptchaResponse = grecaptcha.getResponse();
    // if (reCaptchaResponse.length === 0) {
    //   this.notification.fireAndWaitError({ message: "Captcha Validation Error! please try again!" });
    //   return;
    // }
    let body = {
      uniqueName: this.empUniqueName,
      otp: this.otp,
      newPassword: this.newPassword,
      captchaResponse: reCaptchaResponse
    }
    this.spinner.show();
    this.userService.resetPassword(body)
      .subscribe({
        next: (resp: any) => {
          this.router.navigate(['/login']);
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          reCaptchaResponse.reset();
          this.spinner.hide();
        },
        complete: () => {
          this.spinner.hide();
          reCaptchaResponse.reset();
        }
      })

  }

}
