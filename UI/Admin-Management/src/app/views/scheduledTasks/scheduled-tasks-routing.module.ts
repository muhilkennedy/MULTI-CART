import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ScheduledTasksComponent } from './scheduled-tasks/scheduled-tasks.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'ScheduledTasks',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'scheduledtasks',
      },
      {
        path: 'scheduledtasks',
        component: ScheduledTasksComponent,
        data: {
          title: 'Scheduled Tasks',
        },
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ScheduledTasksRouter {}

