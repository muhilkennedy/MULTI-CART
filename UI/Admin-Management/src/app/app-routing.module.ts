import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DefaultLayoutComponent } from './containers';
import { ErrorComponent } from 'src/common-components/error/error.component';
import { NotFoundComponent } from 'src/common-components/notfound/notfound.component';
import { LoginComponent } from './views/login/login.component';
import { ResetPasswordComponent } from './views/reset-password/reset-password.component';
import { ProfileComponent } from './views/profile/profile.component';
import { TasksComponent } from './views/tasks/tasks.component';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'password/reset',
    component: ResetPasswordComponent,
    pathMatch: 'full'
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'dashboard',
        loadChildren: () =>
          import('./views/dashboard/dashboard.module').then((m) => m.DashboardModule)
      },
      {
        path: 'employee',
        loadChildren: () =>
          import('./views/employee/employee.module').then((m) => m.EmployeeModule)
      },
      {
        path: 'sitesettings',
        loadChildren: () =>
          import('./views/siteSetting/site-settings.module').then((m) => m.SiteSettingsModule)
      },
      {
        path: 'profile',
        loadChildren: () =>
          import('./views/profile/profile.module').then((m) => m.ProfileModule)
      },
      {
        path: 'templates',
        loadChildren: () =>
          import('./views/templates/templates.module').then((m) => m.TemplatesModule)
      },
      {
        path: 'task',
        loadChildren: () =>
          import('./views/tasks/tasks.module').then((m) => m.TasksModule)
      },
      {
        path: 'notifications',
        loadChildren: () =>
          import('./views/user-notification/user-notification.module').then((m) => m.UserNotificationModule)
      },
    ]
  },
  {
    path: "error",
    component: ErrorComponent,
    pathMatch: "full"
  },
  {
    path: "404",
    component: NotFoundComponent,
    pathMatch: "full"
  },
  {
    path: '**', 
    redirectTo: 'login'
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      scrollPositionRestoration: 'top',
      anchorScrolling: 'enabled',
      initialNavigation: 'enabledBlocking'
      // relativeLinkResolution: 'legacy'
    })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
