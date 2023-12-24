import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';
import { TasksService } from 'src/app/service/Task/tasks.service';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.scss']
})
export class CreateTaskComponent implements OnInit {

  taskTypes: string[] = ['TODO', 'APPROVAL'];
  assigneeControl = new FormControl();
  employees: any[] = new Array();
  selectedAssignees: any[] = new Array();
  isBroadcastTask: boolean = false;
  isManagerApproval: boolean = false;
  loadEmpSpinner: boolean = false;

  typeFormGroup: any = this._formBuilder.group({
    type: ['', Validators.required],
  });

  basicFormGroup: any = this._formBuilder.group({
    title: ['', Validators.required],
    description: ['', Validators.required],
    startdate: ['', Validators.required],
    enddate: ['', this.typeFormGroup.controls['type'].value == 'APPROVAL' ? Validators.required : ''],
  });

  constructor(private taskService: TasksService, private _formBuilder: FormBuilder, private router: Router,
      private userService: UserService, private spinner: SpinnerService, private datePipe: DatePipe,
      private notification: NotificationService, private translate: TranslatePipe) {

  }

  public hasError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.hasFormFieldError(fieldGroup, fieldName);
  }

  public getError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.getFieldError(fieldGroup, fieldName, this.translate);
  }

  ngOnInit(): void {
    this.basicFormGroup.controls['startdate'].value = new Date();
  }

  isApprovalTask() {
    return this.typeFormGroup.controls['type'].value == this.taskTypes[1];
  }

  createTask() {
    this.spinner.show();
    let selectedAssigneeIds = this.selectedAssignees.map(assignee => assignee.rootId);
    let body = {
      type: this.typeFormGroup.controls['type'].value,
      title: this.basicFormGroup.controls['title'].value,
      description: this.basicFormGroup.controls['description'].value,
      startDate: this.datePipe.transform(this.basicFormGroup.controls['startdate'].value, CommonUtil.DATE_FORMAT_PLAIN),
      endDate: !CommonUtil.isNullOrEmptyOrUndefined(this.basicFormGroup.controls['enddate'].value) ? this.datePipe.transform(this.basicFormGroup.controls['enddate'].value, CommonUtil.DATE_FORMAT_PLAIN) : null,
      assigneeIds: selectedAssigneeIds,
      broadCast : this.isBroadcastTask,
      managerApproval : this.isManagerApproval
    }
    this.taskService.createTask(body)
      .subscribe({
        next: (resp: any) => {
          this.employees = resp.dataList;
          this.router.navigate(['/task']);
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          this.spinner.hide();
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

  action() {
    if (!CommonUtil.isNullOrEmptyOrUndefined(this.assigneeControl.value) && this.assigneeControl.value.length > 3) {
      this.loadEmployees();
    }
  }

  loadEmployees() {
    this.loadEmpSpinner = true;
    this.userService.getAllMatchingEmployeesForName(this.assigneeControl.value)
      .subscribe({
        next: (resp: any) => {
          this.employees = resp.dataList;
          this.loadEmpSpinner = false;
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          this.loadEmpSpinner = false;
        }
      })
  }

  addAssignee(assignee: any) {
    this.selectedAssignees.push(assignee);
    this.assigneeControl.setValue('');
  }

  remove(assignee: any) {
    const index = this.selectedAssignees.indexOf(assignee);
    if (index > -1) {
      this.selectedAssignees.splice(index, 1);
    }
  }

  canBroadCast() {
    return this.userService.getCurrentUserResponse().userPermissions.includes('ADMIN');
  }

}
