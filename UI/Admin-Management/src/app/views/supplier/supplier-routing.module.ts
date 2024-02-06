import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OnboardSupplierComponent } from './onboard-supplier/onboard-supplier.component';
import { ListSupplierComponent } from './list-supplier/list-supplier.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Supplier',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'onboard',
      },
      {
        path: 'onboard',
        component: OnboardSupplierComponent,
        data: {
          title: 'Onboard',
        },
      },
      {
        path: 'view',
        component: ListSupplierComponent,
        data: {
          title: 'view',
        },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SupplierRoutingModule {}

