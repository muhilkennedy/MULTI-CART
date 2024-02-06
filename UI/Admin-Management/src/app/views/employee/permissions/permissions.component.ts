import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-permissions',
  templateUrl: './permissions.component.html',
  styleUrls: ['./permissions.component.scss']
})
export class PermissionsComponent implements OnInit {

  displayedRolesColumns: string[] = ['RoleName', 'Status', 'Permissions'];
  showPermissionsModal = false;
  showCreateRoleModal = false;

  roles: any[] = new Array();
  permissions: any[] = new Array();

  selectedPermissions: any[] = new Array();

  rolePermissions: any[] = new Array();

  roleFormGroup = this._formBuilder.group({
    roleName: ['', Validators.required],
    rolePermission: ['', Validators.required]
  });

  constructor(private userService: UserService, private _formBuilder: FormBuilder, private spinner: SpinnerService,
    private notification: NotificationService, private translate: TranslatePipe) {
    this.spinner.show();
    this.getAllPermissions();
    this.getAllRoles();
  }

  public hasError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.hasFormFieldError(fieldGroup, fieldName);
  }

  public getError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.getFieldError(fieldGroup, fieldName, this.translate);
  }

  ngOnInit(): void {

  }

  getAllPermissions() {
    this.userService.getAllPermissions()
      .subscribe({
        next: (resp: any) => {
          this.permissions = resp.dataList;
        },
        error: (resp: any) => {

        }
      }
      )
  }

  getAllRoles() {
    this.userService.getAllRoles()
      .subscribe({
        next: (resp: any) => {
          this.roles = resp.dataList;
        },
        error: (resp: any) => {

        },
        complete: () => {
          this.spinner.hide();
        }
      }
      )
  }

  toggleRoleStatus(roleId: any) {
    this.spinner.show();
    this.userService.toggleRoleStatus(roleId)
      .subscribe({
        next: (resp: any) => {
          this.notification.fireAndForget({ message: "Role Status Updated Successfully" }, NotificationType.WARNING);
        },
        error: (resp: any) => {

        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

  togglePermissionsModal() {
    this.showPermissionsModal = !this.showPermissionsModal;
  }

  //done to avoid reopening issue on click outside modal area.
  handlePermissionModal(event: boolean) {
    this.showPermissionsModal = event;
  }

  loadPermissionsModal(permissions: any) {
    this.rolePermissions = permissions;
    this.togglePermissionsModal();
  }

  canCreateRole() {
    return (this.hasError(this.roleFormGroup, 'roleName') || this.hasError(this.roleFormGroup, 'rolePermission'));
  }

  createRoleAction() {
    this.spinner.show();
    let permissionIds: any = new Array();
    let selectedPermissions: any = this.roleFormGroup.value.rolePermission;
    for (let index = 0; index < selectedPermissions.length; index++) {
      permissionIds.push(selectedPermissions[index].rootid);
    }
    let body = {
      name: this.roleFormGroup.value.roleName,
      permissionIds: permissionIds,
    }
    this.userService.createRole(body)
      .subscribe({
        next: (resp: any) => {
          this.getAllRoles();
        },
        error: (err: any) => {
          this.notification.fireAndWait(CommonUtil.generateErrorNotificationFromResponse(err), NotificationType.DANGER);
        }
      }
      )
    this.toggleCreateRoleModal();
  }

  toggleCreateRoleModal() {
    this.showCreateRoleModal = !this.showCreateRoleModal;
  }

  handleCreateRoleModal(event: boolean) {
    this.showCreateRoleModal = event;
  }

}
