import { ChangeDetectorRef, Component, ElementRef, Input, Renderer2, forwardRef } from '@angular/core';
import { ToastComponent, ToasterService } from '@coreui/angular';
import { Colors } from 'src/app/service/util/common-util.service';

@Component({
  selector: 'app-toastnotification',
  templateUrl: './toastnotification.component.html',
  styleUrls: ['./toastnotification.component.scss'],
  providers: [{ provide: ToastComponent, useExisting: forwardRef(() => ToastnotificationComponent) }]
}
)
export class ToastnotificationComponent extends ToastComponent  {

  @Input() closeButton = true;
  @Input() title = '';
  @Input() message = '';

  colours: any = Colors;

  constructor(
    public override hostElement: ElementRef,
    public override renderer: Renderer2,
    public override toasterService: ToasterService,
    public override changeDetectorRef: ChangeDetectorRef)
  {
    super(hostElement, renderer, toasterService, changeDetectorRef);
  }

  isDanger(){
    return this.color == 'danger';
  }

  isWarning(){
    return this.color == 'warning';
  }

  isInfo(){
    return this.color == 'info';
  }

}
