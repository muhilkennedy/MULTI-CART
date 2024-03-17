import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuditLogComponent } from './audit-log/audit-log.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'AuditLogs',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'auditlogs',
      },
      {
        path: 'auditlogs',
        component: AuditLogComponent,
        data: {
          title: 'Audit Logs',
        },
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuditLogsRouter {}

