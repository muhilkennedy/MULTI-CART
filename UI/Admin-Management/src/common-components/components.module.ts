import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { AlertModule, BadgeModule, ButtonModule, CalloutModule, CardModule, FormModule, GridModule, ModalModule, NavModule, PopoverModule, ProgressModule, SharedModule, SpinnerModule, TabsModule, ToastModule, TooltipModule, UtilitiesModule } from '@coreui/angular';
import { IconModule } from '@coreui/icons-angular';
import { ErrorComponent } from './error/error.component';
import { NotFoundComponent } from './notfound/notfound.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { MaterialModule } from 'src/app/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NotificationComponent } from './notification/notification.component';
import { ToastnotificationComponent } from './notification/toastnotification/toastnotification.component';
import { AlertnotificationComponent } from './notification/alertnotification/alertnotification.component';
import { RecaptchaComponent } from './recaptcha/recaptcha.component';

@NgModule({
  declarations: [
    ErrorComponent,
    NotFoundComponent,
    SpinnerComponent,
    NotificationComponent,
    ToastnotificationComponent,
    AlertnotificationComponent,
    RecaptchaComponent
  ],
  exports: [
    ErrorComponent,
    NotFoundComponent,
    SpinnerComponent,
    NotificationComponent,
    ToastnotificationComponent,
    AlertnotificationComponent,
    RecaptchaComponent
  ],
  imports: [
    CommonModule,
    NavModule,
    IconModule,
    RouterModule,
    TabsModule,
    UtilitiesModule,
    CalloutModule,
    SpinnerModule,
    MaterialModule,
    FormsModule,
    ToastModule,
    AlertModule,
    ProgressModule,
    FormsModule,
    ReactiveFormsModule,
    AlertModule,
    GridModule,
    CardModule,
    BadgeModule,
    ButtonModule,
    FormModule,
    ModalModule,
    SharedModule,
    TooltipModule,
    PopoverModule,
    CalloutModule,
    ModalModule
  ]
})
export class ComponentsModule {
}
