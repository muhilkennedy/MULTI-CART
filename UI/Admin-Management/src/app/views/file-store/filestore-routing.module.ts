import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FileStoreComponent } from './file-store.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'File Store',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'filestore',
      },
      {
        path: 'filestore',
        component: FileStoreComponent,
        data: {
          title: 'File Store',
        },
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FileStoreRoutingModule {}

