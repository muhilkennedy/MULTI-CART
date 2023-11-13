import { ChangeDetectorRef, Component, ElementRef, Input, OnInit, QueryList, Renderer2, ViewChildren } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup } from '@angular/forms';
import { ToasterComponent, ToasterPlacement, ToasterService } from '@coreui/angular';
import { Observable, filter } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ToastnotificationComponent } from './toastnotification/toastnotification.component';
import { NotificationService } from '../../app/service/util/notification.service';
import { Colors, CommonUtil } from 'src/app/service/util/common-util.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  @Input() title: string = "Notification";
  @Input() position: string = ToasterPlacement.TopEnd;
  @Input() autohide: boolean = true;
  @Input() delay: number = 5000;
  @Input() color: string = Colors.light;
  @Input() closeButton: boolean = true;
  fade = true;
  props: any = {};

  @ViewChildren(ToasterComponent) viewChildren!: QueryList<ToasterComponent>;

  constructor(private notificationService: NotificationService) {
    this.notificationService.notifyObservable$.subscribe(result => {
      this.props = result;
      if(!CommonUtil.isNullOrEmptyOrUndefined(this.viewChildren))
        this.addToast();
    })
  }

  ngOnInit(): void {


  }

  addToast() {
    if(CommonUtil.isNullOrEmptyOrUndefined(this.props.title)){
      this.props.title = this.title;
    }
    this.viewChildren.forEach((item) => {
        const componentRef = item.addToast(ToastnotificationComponent, this.props, {});
        componentRef.instance['closeButton'] = this.closeButton;
    });
  }

}

