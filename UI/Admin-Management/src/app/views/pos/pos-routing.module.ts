import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PointOfSaleComponent } from './point-of-sale/point-of-sale.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'PointOfSale',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'pos',
      },
      {
        path: 'pos',
        component: PointOfSaleComponent,
        data: {
          title: 'POS',
        },
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PosRoutingModule {}

