import { Component, OnInit } from '@angular/core';

import { navItems } from './_nav';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { INavData } from '@coreui/angular';
import { SubscriberService } from 'src/app/service/Subscriber/subscriber.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { UserService } from 'src/app/service/user/user.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { TenantService } from 'src/app/service/Tenant/tenant.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss'],
})
export class DefaultLayoutComponent implements OnInit {

  public navItems: INavData[] = navItems;

  brandFull={
    src: 'assets/img/tenant/logo.png',
    width: 200,
    height: 46,
    alt: 'Logo'
  };

  brandNarrow={
    src: 'assets/img/tenant/logo.png',
    width: 46,
    height: 46,
    alt: 'Logo'
  }

  constructor(private translate: TranslatePipe, private tenantService: TenantService, 
              private userService: UserService, private router: Router, private cookie: CookieService) {
    this.updateLocale();
  }

  ngOnInit(){
    if(CommonUtil.isNullOrEmptyOrUndefined(this.cookie.get(CommonUtil.TOKEN_KEY))){
      this.router.navigate(['/login']);
    }
    this.userService.pingUser()
    .subscribe(
      {
        next: (resp: any) => {
          this.userService.getCurrentUser().userEmail = resp.data.emailid;
          this.userService.getCurrentUser().userId = resp.data.rootId;
          this.userService.getCurrentUser().userName = resp.data.fname + resp.data.lname;
          this.router.navigate(['/dashboard']);
          this.brandFull = this.getTenantLogo();
          this.brandNarrow = this.brandFull;
        },
        error: (error: any) => {
          this.router.navigate(['/login']);
        },
        complete: () => {
          //done
        }
      }
    )
  }

  getTenantLogo() {
    return CommonUtil.isNullOrEmptyOrUndefined(this.tenantService.getCurrentTenant().details.details.logoUrl) ? "../../../../assets/img/tenant/logo.png" : this.tenantService.getCurrentTenant().details.details.logoUrl;
  }

  updateLocale() {
    for(let i=0; i<navItems.length; i++){
      let name: any = this.navItems[i].name;
      this.navItems[i].name = this.translate.transform(name);
      if(!CommonUtil.isNullOrEmptyOrUndefined(navItems[i].children)){
        let length: any = navItems[i].children?.length;
        let children: any = navItems[i].children;
        for(let j=0; j < length ; j++){
          let childName: any = children[j].name;
          children[j].name = this.translate.transform(childName);
        }
      }
    }
  }

}
