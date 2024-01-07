import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TasksComponent } from './tasks.component';
import { CreateTaskComponent } from './create-task/create-task.component';


const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Tasks',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'task',
      },
      {
        path: 'task',
        component: TasksComponent,
        data: {
          title: 'Task',
        },
      },
      {
        path: 'createtask',
        component: CreateTaskComponent,
        data: {
          title: 'Create Task',
        },
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TasksRoutingModule {}

