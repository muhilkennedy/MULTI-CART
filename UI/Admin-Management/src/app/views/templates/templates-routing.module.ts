import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmailTemplatesComponent } from './email-templates/email-templates.component';
import { InvoiceTemplatesComponent } from './invoice-templates/invoice-templates.component';


const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Templates',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'emailtemplate',
      },
      {
        path: 'emailtemplate',
        component: EmailTemplatesComponent,
        data: {
          title: 'Email Template',
        },
      },
      {
        path: 'invoicetemplate',
        component: InvoiceTemplatesComponent,
        data: {
          title: 'Invoice Template',
        }
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TemplatesRoutingModule {}

