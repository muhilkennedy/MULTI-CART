import { Component, OnInit } from '@angular/core';

import { navItems } from './_nav';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { INavData } from '@coreui/angular';
import { SubscriberService } from 'src/app/service/Subscriber/subscriber.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss'],
})
export class DefaultLayoutComponent implements OnInit {

  public navItems: INavData[] = navItems;

  constructor(private translate: TranslatePipe, private subscriberService: SubscriberService) {
    this.updateLocale();
  }

  ngOnInit(){

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
