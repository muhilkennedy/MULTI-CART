import { DatePipe } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-list-employee',
  templateUrl: './list-employee.component.html',
  styleUrls: ['./list-employee.component.scss']
})
export class ListEmployeeComponent implements OnInit, AfterViewInit {

  employees: any[] = new Array();
  displayedEmployeesColumns: string[] = ['EmployeeId', 'fname', 'Status', 'Mobile', 'Email', 'Designation', 'Actions'];
  employeePermissions: any[] = new Array();
  showPermissionsModal: boolean = false;
  showDeleteModal: boolean  = false;
  showMoreModal: boolean = false;
  deletableEmployee: any;

  totalPages: number = 10;
  pageSize: number = 25;
  pageIndex: number = 0;
  sortBy: string = '';
  sortOrder: string = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  searchText!: string;

  constructor(private userService: UserService, private notification: NotificationService, 
              private spinner: SpinnerService, private datePipe: DatePipe) {

  }

  ngOnInit(): void {
    this.loadEmployees();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => {
      this.pageIndex = 0;
      if(CommonUtil.isNullOrEmptyOrUndefined(this.sort.direction)){
        this.sortBy = '';
        this.sortOrder = this.sort.direction;
      }
      else{
        this.sortBy = this.sort.active;
        this.sortOrder = this.sort.direction;
      }
      this.loadEmployees();
    });
  }

  loadEmployees() {
    this.spinner.show();
    this.userService.getAllEmployees(this.pageIndex, this.pageSize, this.sortBy, this.sortOrder)
      .subscribe({
        next: (resp: any) => {
          this.employees = resp.data.content;
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

  toggleEmployeeStatus(employee: any) {
    this.spinner.show();
    this.userService.toggleEmployeeStatus(employee.rootid)
                    .subscribe({
                      next: (resp: any) => {
                        this.notification.fireAndWaitWarn({message : "Employee " + employee.uniquename + " status has been updated!"});
                      },
                      error: (err: any) => {
                        this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
                      },
                      complete: () => {
                        this.spinner.hide();
                      }
                    })
  }

  loadEmployeePermissionsModal(permissions: any) {
    this.employeePermissions = permissions;
    this.togglePermissionsModal();
  }

  togglePermissionsModal() {
    this.showPermissionsModal = !this.showPermissionsModal;
  }

  //done to avoid reopening issue on click outside modal area.
  handlePermissionModal(event: boolean) {
    this.showPermissionsModal = event;
  }

  loadDeleteModal(employee: any) {
    this.deletableEmployee = employee;
    this.toggleDeleteModal();
  }

  toggleDeleteModal() {
    this.showDeleteModal = !this.showDeleteModal;
  }

  //done to avoid reopening issue on click outside modal area.
  handleDeleteModal(event: boolean) {
    this.showDeleteModal = event;
  }

  handlePageEvent(e: PageEvent) {
    this.totalPages = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.loadEmployees();
  }

  isEmployeeFemale(employee: any){
    if(!CommonUtil.isNullOrEmptyOrUndefined(employee.employeeInfo)){
      return (employee.employeeInfo.gender == "FEMALE");
    }
    return false;
  }

  getDeletableEmployeeUniqueName(){
    if(CommonUtil.isNullOrEmptyOrUndefined(this.deletableEmployee)){
      return '';
    }
    return this.deletableEmployee.uniquename;
  }

  deleteEmployee(){
    //TODO:
    this.toggleDeleteModal();
  }

  showEmployeeBdayCard(employee: any){
    if(!CommonUtil.isNullOrEmptyOrUndefined(employee.employeeInfo) && !CommonUtil.isNullOrEmptyOrUndefined(employee.employeeInfo.dob)){
      return this.datePipe.transform(new Date(), CommonUtil.DATE_FORMAT_PLAIN) == employee.employeeInfo.dob;
    }
    return false;
  }

  loadMoreInfoModal(employee: any){
    this.toggleMoreModal();
  }

  handleMoreModal(event: boolean){
    this.showMoreModal = event;
  }

  toggleMoreModal(){
    this.showMoreModal = !this.showMoreModal;
  }

  applyFilter(event: any){
    if(this.searchText.length > 3){
      this.pageIndex = 0;
      this.totalPages = 0;
      this.spinner.show();
      this.userService.searchEmployeesByNameOrEmail(this.searchText, this.pageSize)
          .subscribe({
            next: (resp: any) => {
              this.employees = resp.dataList;
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

}
