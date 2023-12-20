import { DatePipe } from '@angular/common';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { TranslatePipe } from '@ngx-translate/core';
import { TasksService } from 'src/app/service/Task/tasks.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-list-tasks',
  templateUrl: './list-tasks.component.html',
  styleUrls: ['./list-tasks.component.scss']
})
export class ListTasksComponent implements OnInit, OnChanges {

  tasks: any = new Array();
  taskColumns: string[] = ['TaskType', 'Title', 'Description', 'StartDate', 'DueDate', 'CreatedBy', 'Assignees', 'Actions'];

  totalPages: number = 10;
  pageSize: number = 25;
  pageIndex: number = 0;

  @Input() status: string = '';
  @Input() type: string = '';

  constructor(private taskService: TasksService, private spinner: SpinnerService, private translate: TranslatePipe,
              private notification: NotificationService, private datepipe: DatePipe) {
     
  }

  ngOnChanges(changes: SimpleChanges): void {
    //console.log(changes)
    // if(changes['status'].currentValue != this.status){
    //   this.loadTasks();
    // }
    // this.status = changes['status'].currentValue;
  }

  ngOnInit(): void {
    if(!CommonUtil.isNullOrEmptyOrUndefined(this.status)){
      this.loadTasks();
    }
  }

  getDueDate(date: any){
    if(CommonUtil.isNullOrEmptyOrUndefined(date)){
      return this.translate.transform("NO EXPIRY");
    }
    else{
      return this.datepipe.transform(date, CommonUtil.DATE_FORMAT_PLAIN);
    }     
  }

  handlePageEvent(e: PageEvent) {
    this.totalPages = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.loadTasks();
  }

  loadTasks(){
    this.spinner.show();
    this.taskService.getTasks(this.type, this.status, this.status =='Broadcasted')
      .subscribe({
        next: (resp: any) => {
          this.tasks = resp.data.content;
          this.totalPages = resp.data.totalElements;
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }
}
