import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  public visible: boolean = false;
  public notify = new BehaviorSubject<any>('');
  notifyObservable$ = this.notify.asObservable();

  public show(): void{
    this.notify.next(true);
  }

  public hide(): void{
    this.notify.next(false);
  }

}
