import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-onboard-employee',
  templateUrl: './onboard-employee.component.html',
  styleUrls: ['./onboard-employee.component.scss']
})
export class OnboardEmployeeComponent implements OnInit {

  isLinear = true;

  gender: string = 'MALE';
  genders: Array<string> = ["MALE", 'FEMALE'];

  //TODO: can be moved to a typeahaed component
  assigneeControl = new FormControl('');
  employees: any[] = new Array();
  selectedAssignee: any = null;
  loadEmpSpinner: boolean = false;

  basicFormGroup: any = this._formBuilder.group({
    fname: ['', Validators.required],
    lname: ['', Validators.required],
    emailId: ['', [Validators.required, Validators.email]],
    mobile: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern("^[0-9]+$")]],
    dob: ['', Validators.required]
  });

  constructor(private _formBuilder: FormBuilder, private translate: TranslatePipe,
    private userService: UserService, private spinner: SpinnerService,
    private notificationService: NotificationService, private datePipe: DatePipe) { }

  public hasError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.hasFormFieldError(fieldGroup, fieldName);
  }

  public getError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.getFieldError(fieldGroup, fieldName, this.translate);
  }

  advFormGroup = this._formBuilder.group({
    roles: ['', Validators.required],
    file: ['', Validators.required],
    designation: ['', Validators.required]
  });

  allRoles: Array<any> = new Array();
  selectedFile!: File;

  ngOnInit(): void {
    this.genders = this.genders.map(gen => {
      return this.translate.transform(gen);
    });

  }

  onFileSelected(fileInputEvent: any) {
    this.selectedFile = fileInputEvent.target.files[0];
  }

  getFileName(): string {
    if (this.selectedFile) {
      return this.selectedFile.name;
    }
    return "";
  }

  onGenderSelection(gender: string){
    this.gender = gender;
  }

  basicStepCompleteAction() {
    if (this.allRoles.length == 0) {
      this.spinner.show();
      this.userService.getAllRoles()
        .subscribe({
          next: (resp: any) => {
            this.allRoles = resp.dataList;
          },
          error: (resp: any) => {
            this.notificationService.fireAndWait({ title: "Role Fetch Error", message: resp.message }, NotificationType.DANGER);
          },
          complete: () => {
            this.spinner.hide();
          }
        })
    }
  }

  onboardEmployee() {
    this.spinner.show();
    let roleIds = this.allRoles.filter(role => this.advFormGroup.controls['roles'].value?.includes(role.rolename)).map(role => role.rootid);
    let body = {
      fname: this.basicFormGroup.controls['fname'].value,
      lname: this.basicFormGroup.controls['lname'].value,
      emailId: this.basicFormGroup.controls['emailId'].value,
      mobile: this.basicFormGroup.controls['mobile'].value,
      designation: this.advFormGroup.controls['designation'].value,
      reportsTo : CommonUtil.isNullOrEmptyOrUndefined(this.selectedAssignee)? null : this.selectedAssignee.uniquename,
      dob: this.datePipe.transform(this.basicFormGroup.controls['dob'].value, CommonUtil.DATE_FORMAT_PLAIN),
      gender: this.gender,
      roleIds: roleIds,
    }
    this.userService.createEmployee(body)
                    .subscribe({
                      next: (resp: any) => {
                        this.notificationService.fireAndWait({ message : "Employee Onboarded Successfully!"}, NotificationType.PRIMARY);
                        this.userService.updateEmployeeProof(resp.data.uniquename, this.selectedFile)
                                        .subscribe({
                                          error: (err: any) => {
                                            this.notificationService.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
                                          },
                                          complete: () => {
                                            this.spinner.hide();
                                          }
                                        })
                      },
                      error: (err: any) => {
                        this.notificationService.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
                        this.spinner.hide();
                      }
                    })
  }

  action() {
    if (!CommonUtil.isNullOrEmptyOrUndefined(this.assigneeControl.value) && this.assigneeControl.value!.length > 3) {
      this.loadEmployees();
    }
  }

  loadEmployees() {
    this.loadEmpSpinner = true;
    this.userService.getAllMatchingEmployeesForName(this.assigneeControl.value!)
      .subscribe({
        next: (resp: any) => {
          this.employees = resp.dataList;
          this.loadEmpSpinner = false;
        },
        error: (err: any) => {
          this.notificationService.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          this.loadEmpSpinner = false;
        }
      })
  }

  addAssignee(employee: any) {
    this.selectedAssignee = employee;
  }

  getFullName(employee: any){
    return employee.fname + " " + employee.lname;
  }

}
