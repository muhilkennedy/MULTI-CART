import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.scss']
})
export class EditEmployeeComponent implements OnInit{

  empUniqueName!: string;
  employee: any;
  roles: any;

  roleFormControl = new FormControl('');

  contact!: number;

  constructor(private userService: UserService, private spinner: SpinnerService, private translate: TranslatePipe,
              private notification: NotificationService){

  }
  
  ngOnInit(): void {
    this.getAllRoles();
  }

  showOthersText(length: any){
   return length > 2 ? this.translate.transform("others") : this.translate.transform("other");
  }

  getProfilePicture() {
    if (CommonUtil.isNullOrEmptyOrUndefined(this.employee.employeeInfo) || CommonUtil.isNullOrEmptyOrUndefined(this.employee.employeeInfo.profilepic)) {
      return "../../../assets/img/avatars/profile.png";
    }
    else {
      return this.employee.employeeInfo.profilepic;
    }
  }

  searchEmployee(){
    this.spinner.show();
    this.userService.findEmployeeByUniqueName(this.empUniqueName)
        .subscribe({
          next: (resp: any) => {
            this.employee = resp.data;
            this.contact = this.employee.mobile;
            this.userService.getAllEmployeeRoles(this.employee.rootid)
            .subscribe({
              next: (resp: any) => {
                let rolesList: any = resp.dataList;
                let empCurrentRoles: any = new Array();
                rolesList.forEach((element:any) => {
                  empCurrentRoles.push(element.rolename);
                });
                this.roleFormControl.setValue(empCurrentRoles);
              },
              error: (err: any) => {
                this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
              },
              complete: () => {
                this.spinner.hide();
              }
            });
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err))
          }
        });
  }

  getAllRoles() {
    this.userService.getAllRoles()
      .subscribe({
        next: (resp: any) => {
          this.roles = resp.dataList;
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err))
        },
        complete: () => {
          this.spinner.hide();
        }
      }
      )
  }

  onContactUpdate(){

  }

  roleChanged(event: any){
    if(event.isUserInput){
      let roleId = this.roles.filter((role: any) => role.rolename == event.source.value);
      if(!(this.isCurrentActiveRole(roleId[0].rolename) && event.source._selected)){
        this.spinner.show();
        this.userService.updateEmployeeRole(roleId[0].rootid, this.employee.rootid, event.source._selected)
        .subscribe({
          next: (resp: any) => {
            this.notification.fireAndWaitWarn(CommonUtil.generateSimpleNotificationMessage("Employee Role(s) Has been Updated"));
          },
          error: (err : any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err))
          },
          complete: () => {
            this.spinner.hide();
          }
        });
      }
    }
  }

  isCurrentActiveRole(roleName: any){
    let currRoles:any = this.roleFormControl.value;
    return currRoles.includes(roleName);
  }

}
