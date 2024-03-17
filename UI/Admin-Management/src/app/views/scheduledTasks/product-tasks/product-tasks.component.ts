import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-product-tasks',
  templateUrl: './product-tasks.component.html',
  styleUrls: ['./product-tasks.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class ProductTasksComponent implements OnInit {

  displayedColumns: any[] = ["jobname", "jobgroup", "jobstatus", "timeupdated", "errorinfo", "expand"];
  expandedElement: any | null;
  
  searchText!: string;
  tasks: any[] = [];

  totalPages: number = 10;
  pageSize: number = 10;
  pageIndex: number = 0;

  ngOnInit(): void {
    this.loadTMTasks();
  }

  handlePageEvent(e: PageEvent) {
    this.totalPages = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.loadTMTasks();
  }

  loadTMTasks(){

  }

}
