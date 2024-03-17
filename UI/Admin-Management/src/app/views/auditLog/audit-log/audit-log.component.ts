import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-audit-log',
  templateUrl: './audit-log.component.html',
  styleUrls: ['./audit-log.component.scss']
})
export class AuditLogComponent implements OnInit {

  displayedAuditColumns: any[] = ["auditid", "operation", "message", "timecreated"];
  searchText!: string;
  audits!: any[];

  totalPages: number = 10;
  pageSize: number = 25;
  pageIndex: number = 0;

  constructor(private spinner: SpinnerService){

  }

  ngOnInit(): void {
    this.loadLogs();
  }

  handlePageEvent(e: PageEvent) {
    this.totalPages = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.loadLogs();
  }

  loadLogs(){

  }

}
