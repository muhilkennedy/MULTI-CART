import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SiteSettingsComponent } from './site-settings/site-settings.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'SiteSettings',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'sitesettings',
      },
      {
        path: 'sitesettings',
        component: SiteSettingsComponent,
        data: {
          title: 'Onboard',
        },
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SiteSettingsRoutingModule {}

