import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { SpinnerService } from '../../app/service/util/sipnner.service';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss']
})
export class SpinnerComponent implements OnInit {

  @Input() color?: string = "warn";
  @Input() diameter?: number = 50;
  @Input() show: boolean = false;

  constructor(private spinner: SpinnerService){
    this.spinner.notifyObservable$.subscribe(result => {
      this.show = result;
    })
  }

  ngOnInit(): void {
    
    
  }

}
