import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { AuditlogsService } from 'src/app/service/audit/auditlogs.service';

@Component({
  selector: 'app-product-log',
  templateUrl: './product-log.component.html',
  styleUrls: ['./product-log.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class ProductLogComponent implements OnInit {

  displayedColumns: any[] = ["auditid", "operation", "timeupdated", "expand"];
  expandedElement: any | null;
  
  loader1: boolean = false;
  auditId!: string;
  logs: any[] = [];

  totalPages: number = 10;
  pageSize: number = 10;
  pageIndex: number = 0;

  constructor(private auditService: AuditlogsService){

  }

  ngOnInit(): void {
    this.loadPMLogs();
  }

  handlePageEvent(e: PageEvent) {
    this.totalPages = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.loadPMLogs();
  }

  loadPMLogs(){
    this.loader1 = true;
    this.auditService.getPMLogs(this.pageIndex, this.pageSize)
        .subscribe({
          next: (resp: any) => {
              this.logs = resp.data.content;
              this.totalPages = resp.data.totalElements;
          },
          error: (err: any) => {

          },
          complete: () => {
            this.loader1=false;
          }
        });
  }

}
