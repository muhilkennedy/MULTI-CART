import { Component, OnInit } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { PageEvent } from '@angular/material/paginator';
import { AuditlogsService } from 'src/app/service/audit/auditlogs.service'

@Component({
  selector: 'app-tenant-log',
  templateUrl: './tenant-log.component.html',
  styleUrls: ['./tenant-log.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class TenantLogComponent implements OnInit {

  displayedColumns: any[] = ["auditid", "operation", "timeupdated", "expand"];
  expandedElement: any | null;
 
  loader: boolean = false;
  auditId!: string;
  logs: any[] = [];

  totalPages: number = 10;
  pageSize: number = 10;
  pageIndex: number = 0;

  constructor(private auditService: AuditlogsService){

  }

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
    this.loader = true;
    this.auditService.getTMLogs(this.pageIndex, this.pageSize)
        .subscribe({
          next: (resp: any) => {
            this.logs = resp.data.content;
            this.totalPages = resp.data.totalElements;
          },
          error: (err: any) => {

          },
          complete: () => {
            this.loader=false;
          }
        });
  }

}
