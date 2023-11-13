import { Component, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

import { ClassToggleService, HeaderComponent } from '@coreui/angular';
import { TranslateService } from '@ngx-translate/core';
import { SupportedLanguages } from 'src/app/i18n/i18n.module';
import { SubscriberService } from '../../../service/Subscriber/subscriber.service';
import { CookieService } from 'ngx-cookie-service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-default-header',
  templateUrl: './default-header.component.html',
})
export class DefaultHeaderComponent extends HeaderComponent {

  @Input() sidebarId: string = "sidebar";

  languages: any;
  selectedLanguage: any;

  public newMessages = new Array(4)
  public newTasks = new Array(5)
  public newNotifications = new Array(5)

  constructor(public translate: TranslateService, private router: Router,
              private cookieService: CookieService) {
    super();
    this.languages = SupportedLanguages.languages;
  }

  changeLanguage(langCode: string){
    this.translate.use(langCode);
    this.cookieService.set("lang", langCode);
    window.location.reload();
  }

  logoutAction(){
    this.cookieService.delete(CommonUtil.TOKEN_KEY);
    this.router.navigate(['']);
  }

}
