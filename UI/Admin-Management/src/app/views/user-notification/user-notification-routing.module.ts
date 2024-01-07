import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserNotificationComponent } from './user-notification/user-notification.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Notifications',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'notifications',
      },
      {
        path: 'notifications',
        component: UserNotificationComponent,
        data: {
          title: 'User Notifications',
        },
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserNotificationRouter {}

