import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OnboardEmployeeComponent} from './onboard-employee/onboard-employee.component';
import {EditEmployeeComponent} from './edit-employee/edit-employee.component';
import {ListEmployeeComponent} from './list-employee/list-employee.component';
import { PermissionsComponent } from './permissions/permissions.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Employee',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'onboard',
      },
      {
        path: 'onboard',
        component: OnboardEmployeeComponent,
        data: {
          title: 'Onboard',
        },
      },
      {
        path: 'edit',
        component: EditEmployeeComponent,
        data: {
          title: 'Edit',
        },
      },
      {
        path: 'view',
        component: ListEmployeeComponent,
        data: {
          title: 'View',
        },
      },
      {
        path: 'permissions',
        component: PermissionsComponent,
        data: {
          title: 'Permissions',
        },
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EmployeeRoutingModule {}

