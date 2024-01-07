import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { AlertModule, BadgeModule, ButtonModule, CalloutModule, CardModule, FormModule, GridModule, ModalModule, NavModule, PopoverModule, ProgressModule, SharedModule, SpinnerModule, TabsModule, ToastModule, TooltipModule, UtilitiesModule, WidgetModule } from '@coreui/angular';
import { IconModule } from '@coreui/icons-angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material.module';
import { PendingtasksWidgetComponent } from './pendingtasks-widget/pendingtasks-widget.component';
import { TranslateModule } from '@ngx-translate/core';
import { NotificationsWidgetComponent } from './notifications-widget/notifications-widget.component';
import { CalendarWidgetComponent } from './calendar-widget/calendar-widget.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

@NgModule({
  declarations: [
    PendingtasksWidgetComponent,
    NotificationsWidgetComponent,
    CalendarWidgetComponent
  ],
  exports: [
    PendingtasksWidgetComponent,
    NotificationsWidgetComponent,
    CalendarWidgetComponent
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
    WidgetModule,
    TranslateModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
  ]
})
export class WidgetsModule {
}
