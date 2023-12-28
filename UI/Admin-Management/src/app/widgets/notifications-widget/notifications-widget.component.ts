import { Component, OnInit } from '@angular/core';
import { cilBell, cilArrowRight } from '@coreui/icons';
import { WidgetService } from 'src/app/service/widget/widget.service';

@Component({
  selector: 'app-notifications-widget',
  templateUrl: './notifications-widget.component.html',
  styleUrls: ['./notifications-widget.component.scss']
})
export class NotificationsWidgetComponent implements OnInit{
  icons = { cilBell, cilArrowRight };
  count: number = 0;

  constructor(public widgetService: WidgetService){

  }

  ngOnInit(): void {

  }
}
