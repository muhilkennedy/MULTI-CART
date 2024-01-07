import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// CoreUI Modules
import {
  AccordionModule,
  AlertModule,
  BadgeModule,
  BreadcrumbModule,
  ButtonModule,
  CalloutModule,
  CardModule,
  CarouselModule,
  CollapseModule,
  DropdownModule,
  FormModule,
  GridModule,
  ListGroupModule,
  ModalModule,
  NavModule,
  PaginationModule,
  PlaceholderModule,
  PopoverModule,
  ProgressModule,
  SharedModule,
  SpinnerModule,
  TableModule,
  TabsModule,
  TooltipModule,
  UtilitiesModule
} from '@coreui/angular';
import { IconModule } from '@coreui/icons-angular';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { I18nModule } from 'src/app/i18n/i18n.module';
import { MaterialModule } from 'src/app/material.module';
import { UserNotificationRouter } from './user-notification-routing.module';
import { UserNotificationComponent } from './user-notification/user-notification.component';

@NgModule({
  imports: [
    UserNotificationRouter,
    CommonModule,
    AccordionModule,
    BadgeModule,
    BreadcrumbModule,
    ButtonModule,
    CardModule,
    CollapseModule,
    GridModule,
    UtilitiesModule,
    SharedModule,
    ListGroupModule,
    IconModule,
    ListGroupModule,
    PlaceholderModule,
    ProgressModule,
    SpinnerModule,
    TabsModule,
    NavModule,
    TooltipModule,
    CarouselModule,
    FormModule,
    ReactiveFormsModule,
    DropdownModule,
    PaginationModule,
    PopoverModule,
    TableModule,
    TranslateModule,
    CalloutModule,
    ModalModule,
    I18nModule,
    MaterialModule,
    AlertModule
  ],
  declarations: [
    UserNotificationComponent
  ],
})
export class UserNotificationModule { }
