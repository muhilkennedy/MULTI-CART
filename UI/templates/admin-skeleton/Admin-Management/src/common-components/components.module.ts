import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { CalloutModule, NavModule, TabsModule, UtilitiesModule } from '@coreui/angular';
import { IconModule } from '@coreui/icons-angular';
import { ErrorComponent } from './error/error.component';
import { NotFoundComponent } from './notfound/notfound.component';

@NgModule({
  declarations: [
    ErrorComponent,
    NotFoundComponent
  ],
  exports: [
    ErrorComponent,
    NotFoundComponent
  ],
  imports: [
    CommonModule,
    NavModule,
    IconModule,
    RouterModule,
    TabsModule,
    UtilitiesModule,
    CalloutModule
  ]
})
export class ComponentsModule {
}
