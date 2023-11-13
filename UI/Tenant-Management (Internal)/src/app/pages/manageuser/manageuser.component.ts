import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { TenantService } from 'src/app/service/Tenant/tenant.service';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';

@Component({
  selector: 'app-manageuser',
  templateUrl: './manageuser.component.html',
  styleUrls: ['./manageuser.component.scss']
})
export class ManageuserComponent implements OnInit {

  fname: string;
  lname: string;
  emailId: string;
  mobile: string;
  dob: Date;
  gender: string;
  genders: string[] = ['Male', 'Female', 'Others'];

  public datePipe: DatePipe;
  dateForm = new FormControl(new Date());

  userlist: any[];
  selectedRealm: string = '';
  realms: any[];
  roles: any[];
  selectedRoles: any[];
  roleName: string;
  permissions: any[];
  selectedPermissions:any[];

  formControl = new FormControl(Validators.required);
  rolesFormControl = new FormControl('');

  constructor(private http: HttpClient,private toastr: ToastrService, private cookieService: CookieService, private userService: UserService, private tenantService: TenantService) { }

  ngOnInit(): void {
    this.datePipe = new DatePipe('en-GB');
    this.tenantService.getAllTenants()
    .subscribe({
      next: (resp: any) => {
        this.realms = resp.dataList;
      },
      error: (error) => {
        this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error loading tenants : ' + error.error.message + '</b>.', '', {
          disableTimeOut: false,
          closeButton: true,
          enableHtml: true,
          toastClass: "alert alert-error alert-with-icon",
          positionClass: 'toast-' + 'bottom' + '-' + 'center'
        });
      }
    });
    this.getAllPermissions();
  }

  getCSAUsers(tenantId){
    this.userService.fetchSuperUsers(tenantId)
      .subscribe({
        next : (resp) => {
          this.userlist = resp.dataList;
        },
        error: (error) =>{
          this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error loading users : ' + error.error.message + '</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-error alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' + 'center'
          });
        }
      }
      );
    this.getAllRoles(tenantId);
  }

  onSave(){
    let selectedRoleIds: number[] = new Array();
    this.selectedRoles.forEach(role => {
      this.roles.forEach(r => {
        if(r.rolename == role){
          selectedRoleIds.push(r.rootId);
        }
      })
    })
    const body = {
      emailId: this.emailId,
      fname: this.fname,
      lname: this.lname,
      mobile: this.mobile,
      gender: this.gender,
      dob: this.datePipe.transform(this.dateForm.value, 'yyyy-MM-dd'),
      roleIds: selectedRoleIds
    };
    this.userService.registerUser(body, this.selectedRealm)
      .subscribe({
        next: (resp:any) => {
          this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Admin user ' + this.fname + ' added successfully</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-success alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' +  'center'
          });
          this.getCSAUsers(this.selectedRealm);
        },
        error: (error:any) => {
            console.log("error in loading tenant");
            this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error Adding user : ' + error.error.message +'</b>.', '', {
              disableTimeOut: false,
              closeButton: true,
              enableHtml: true,
              toastClass: "alert alert-error alert-with-icon",
              positionClass: 'toast-' + 'bottom' + '-' +  'center'
            });
        }
  });
  }

  public toggleActive(event: MatSlideToggleChange, rootId: string) {
    this.userlist.filter(user => user.rootId == rootId).map(user => user.active = event.checked);
    this.userService.toggleUserState(this.selectedRealm)
      .subscribe({
        next: (resp: any) => {
          this.toastr.success('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Admin user ' + this.fname + ' status updated</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-success alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' + 'center'
          });
        },
        error: (error: any) => {
          console.log("error in loading tenant");
          this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error updating user : ' + error.error.message + '</b>.', '', {
            disableTimeOut: false,
            closeButton: true,
            enableHtml: true,
            toastClass: "alert alert-error alert-with-icon",
            positionClass: 'toast-' + 'bottom' + '-' + 'center'
          });
        }
      });
  }

  public shouldDisable(userId: string){
    return userId==this.userService.getCurrentUser().userId;
  }

  getAllRoles(tenantId){
    this.userService.getAllRoles(tenantId)
        .subscribe({
          next: (resp:any) => {
              this.roles = resp.dataList;
          },
          error: (error) => {
            this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error loading roles : ' + error.error.message + '</b>.', '', {
              disableTimeOut: false,
              closeButton: true,
              enableHtml: true,
              toastClass: "alert alert-error alert-with-icon",
              positionClass: 'toast-' + 'bottom' + '-' + 'center'
            });
          }
        })
  }

  getAllPermissions(){
    this.userService.getAllPermissions()
        .subscribe({
          next: (resp:any) => {
              this.permissions = resp.dataList;
          },
          error: (error) => {
            this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error loading permissions : ' + error.error.message + '</b>.', '', {
              disableTimeOut: false,
              closeButton: true,
              enableHtml: true,
              toastClass: "alert alert-error alert-with-icon",
              positionClass: 'toast-' + 'bottom' + '-' + 'center'
            });
          }
        })
  }

  onSaveRole(){
    let selectedIds: number[] = new Array();
    this.selectedPermissions.forEach(perm => {
      selectedIds.push(perm.rootid);
    })
    let body = {
      name: this.roleName,
      permissionIds: selectedIds
    }
    this.userService.createRole(body, this.selectedRealm)
    .subscribe({
      next: (resp:any) => {
          this.getAllRoles(this.selectedRealm);
      },
      error: (error) => {
        this.toastr.error('<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b>Error creating Role : ' + error.error.message + '</b>.', '', {
          disableTimeOut: false,
          closeButton: true,
          enableHtml: true,
          toastClass: "alert alert-error alert-with-icon",
          positionClass: 'toast-' + 'bottom' + '-' + 'center'
        });
      }
    })
  }


}
