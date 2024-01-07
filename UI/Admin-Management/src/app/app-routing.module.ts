import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DefaultLayoutComponent } from './containers';
import { ErrorComponent } from 'src/common-components/error/error.component';
import { NotFoundComponent } from 'src/common-components/notfound/notfound.component';
import { LoginComponent } from './views/login/login.component';

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
        path: 'pos',
        loadChildren: () =>
          import('./views/pos/pos.module').then((m) => m.PosModule)
      }
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
