import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddProductComponent } from './add-product/add-product.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { ViewProductComponent } from './view-product/view-product.component';
import { CategoryComponent } from './category/category.component';
import { EditProductInfoComponent } from './edit-product-info/edit-product-info.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Product',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'add',
      },
      {
        path: 'add',
        component: AddProductComponent,
        data: {
          title: 'Add-Product',
        },
      },
      {
        path: 'edit',
        component: EditProductComponent,
        data: {
          title: 'Edit-Product',
        },
      },
      {
        path: 'view',
        component: ViewProductComponent,
        data: {
          title: 'View-Product',
        },
      },
      {
        path: 'productinfo',
        component: EditProductInfoComponent,
        data: {
          title: 'Product Info',
        },
      },
      {
        path: 'category',
        component: CategoryComponent,
        data: {
          title: 'Category',
        },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProductRoutingModule {}

