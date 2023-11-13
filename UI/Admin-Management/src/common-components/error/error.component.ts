import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit {

  message: any = "Server is not Reachable!";

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    let message =  this.route.snapshot.paramMap.get("message");
    if(message != null || message != undefined){
      this.message = message;
    }
  }

}
