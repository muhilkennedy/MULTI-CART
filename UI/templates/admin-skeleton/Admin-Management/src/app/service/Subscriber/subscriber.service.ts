import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, of, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriberService {

  public notify = new BehaviorSubject<any>('');

  notifyObservable$ = this.notify.asObservable();
  
  public notifyAll(data: any)
  {
      if (data) {
          this.notify.next(data);
      }
  }

}

/*
inside component to publish:
this.subscriberService.notifyAll({msg: msg});

inside component to listen:
this.subscriberService.notifyObservable$.subscribe(res => {
          if(res){
              // get your grid data again.
          }
    })
*/
