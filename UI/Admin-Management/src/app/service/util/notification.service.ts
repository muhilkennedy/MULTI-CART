import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { SpinnerService } from "./sipnner.service";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  public notify = new BehaviorSubject<any>('');
  notifyObservable$ = this.notify.asObservable();

  constructor(private spinner: SpinnerService){

  }

  /**
   * props = {
   *  title : ''
   *  message : ''
   * }
   */
  public show(props: any): void{
    this.notify.next(props);
  }

  public fireAndWaitDefault(props: any): void{
    this.fireAndWait(props, NotificationType.PRIMARY);
    this.spinner.hide();
  }

  public fireAndWait(props: any, type: NotificationType): void{
    props.autohide = false;
    props.color = type.toString(),
    this.notify.next(props);
    this.spinner.hide();
  }

  public fireAndWaitError(props: any): void{
    props.autohide = false;
    props.color = NotificationType.DANGER,
    this.notify.next(props);
    this.spinner.hide();
  }

  public fireAndWaitWarn(props: any): void{
    props.autohide = false;
    props.color = NotificationType.WARNING,
    this.notify.next(props);
    this.spinner.hide();
  }

  //Notification autohides in 5seconds
  public fireAndForget(props: any, type: NotificationType){
    props.autohide = true;
    props.color = type.toString(),
    this.notify.next(props);
    this.spinner.hide();
  }

}

export enum NotificationType {
  DANGER="danger",
  PRIMARY="",
  WARNING="warning",
  INFO="info"
}
