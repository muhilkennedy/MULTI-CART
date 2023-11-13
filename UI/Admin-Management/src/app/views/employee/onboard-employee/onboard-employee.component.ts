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

  constructor(private _formBuilder: FormBuilder, private translate: TranslatePipe, 
              private userService: UserService, private spinner: SpinnerService,
              private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.genders = this.genders.map(gen => {
      return this.translate.transform(gen);
    });
  }

  basicFormGroup: any = this._formBuilder.group({
    fname: ['', Validators.required],
    lname: ['', Validators.required],
    emailId: ['', [Validators.required, Validators.email]],
    mobile: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern("^[0-9]+$")]],
    dob: ['', Validators.required]
  });
  
  public hasError = (fieldGroup: any, fieldName: string) =>{
    return CommonUtil.hasFormFieldError(fieldGroup, fieldName);
  }

  public getError = (fieldGroup: any, fieldName: string) =>{
    return CommonUtil.getFieldError(fieldGroup, fieldName);
  }

  advFormGroup = this._formBuilder.group({
    roles: ['', Validators.required],
    file: ['', Validators.required]
  });

  allRoles: Array<any> = new Array();
  selectedFile!: File;

  onFileSelected(fileInputEvent: any) {
   this.selectedFile = fileInputEvent.target.files[0];
  }

  getFileName(): string{
    if(this.selectedFile){
      return this.selectedFile.name;
    }
    return "";
  }

  basicStepCompleteAction(){
    if(this.allRoles.length==0){
      this.spinner.show();
      this.userService.getAllRoles()
          .subscribe({
            next: (resp: any) => {
              this.allRoles = resp.dataList;
            },
            error: (resp: any) => {
              this.notificationService.fireAndWait({title : "Role Fetch Error", message: resp.message}, NotificationType.DANGER);
            },
            complete: () => {
              this.spinner.hide();
            }
          })
    }
  }

}
