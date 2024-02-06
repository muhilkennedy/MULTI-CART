import { Component, OnInit } from '@angular/core';

import { navItems } from './_nav';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { INavData } from '@coreui/angular';
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

  public navItems: INavData[] = new Array();

  brandFull: any;
  brandNarrow: any;

  constructor(private translate: TranslatePipe, private tenantService: TenantService,
    private userService: UserService, private router: Router, private cookie: CookieService) {
    // this.updateLocale();
    this.brandFull = {
      src: this.getTenantLogo(),
      width: 200,
      height: 46,
      alt: 'Logo'
    };
    this.brandNarrow = {
      src: this.getTenantLogo(),
      width: 46,
      height: 46,
      alt: 'Logo'
    };
  }

  getNavItems(): INavData[] {
    return this.navItems;
  }

  isValid(attributes: any) {
    return !CommonUtil.isNullOrEmptyOrUndefined(attributes) && !this.userService.doesUserHavePermission(attributes["Permission"]);
  }

  ngOnInit() {
    if (CommonUtil.isNullOrEmptyOrUndefined(this.cookie.get(CommonUtil.TOKEN_KEY))) {
      this.router.navigate(['/login']);
    }
    this.userService.pingUser()
      .subscribe(
        {
          next: (resp: any) => {
            this.userService.getCurrentUser().userEmail = resp.data.emailid;
            this.userService.getCurrentUser().userId = resp.data.rootId;
            this.userService.getCurrentUser().userName = resp.data.fname + resp.data.lname;
            this.userService.setCurrentUserResponse(resp.data);
            //restrict menu based on user permission
            let finalNavItems: INavData[] = new Array();
            for (let i = 0; i < navItems.length; i++) {
              let attributes: any = navItems[i].attributes;
              if (!(!CommonUtil.isNullOrEmptyOrUndefined(attributes) && !this.userService.doesUserHavePermission(attributes["Permission"]))) {
                let children: any = navItems[i].children;
                let finalChildNavItems: INavData[] = new Array();
                for(let j =0; !CommonUtil.isNullOrEmptyOrUndefined(children) && j < children.length; j++){
                  attributes = children[j].attributes;
                  if (!(!CommonUtil.isNullOrEmptyOrUndefined(attributes) && !this.userService.doesUserHavePermission(attributes["Permission"]))) {
                    finalChildNavItems.push(children[j]);
                  }
                }
                let item: INavData = {};
                item = navItems[i];
                item.children = finalChildNavItems;
                // finalNavItems.push(navItems[i]);
                finalNavItems.push(item);
              }
            }
            this.navItems = finalNavItems;
            //this.navItems = navItems;
            this.updateLocale();
            //dashbord redirect
            this.router.navigate(['/dashboard']);
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
    for (let i = 0; i < this.navItems.length; i++) {
      let name: any = this.navItems[i].name;
      this.navItems[i].name = this.translate.transform(name);
      if (!CommonUtil.isNullOrEmptyOrUndefined(this.navItems[i].children)) {
        let length: any = this.navItems[i].children?.length;
        let children: any = this.navItems[i].children;
        for (let j = 0; j < length; j++) {
          let childName: any = children[j].name;
          children[j].name = this.translate.transform(childName);
        }
      }
    }
  }

}
