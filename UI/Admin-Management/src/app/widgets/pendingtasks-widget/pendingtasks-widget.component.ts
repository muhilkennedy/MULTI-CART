import { Component, OnInit } from '@angular/core';
import { cilTask, cilArrowRight } from '@coreui/icons';
import { WidgetService } from 'src/app/service/widget/widget.service';

@Component({
  selector: 'app-pendingtasks-widget',
  templateUrl: './pendingtasks-widget.component.html',
  styleUrls: ['./pendingtasks-widget.component.scss']
})
export class PendingtasksWidgetComponent implements OnInit {
  
  icons = { cilTask, cilArrowRight };

  constructor(public widgetService: WidgetService){

  }

  ngOnInit(): void {
  }

}
