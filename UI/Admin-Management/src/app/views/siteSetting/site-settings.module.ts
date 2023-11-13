import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// CoreUI Modules
import {
  AccordionModule,
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
import { I18nModule } from '../../i18n/i18n.module'
import { ComponentsModule } from '../../../common-components/components.module';
import { MaterialModule } from '../../material.module';
import { SiteSettingsComponent } from './site-settings/site-settings.component';
import { SiteSettingsRoutingModule } from './sitesettings-routing.module';

@NgModule({
  imports: [
    SiteSettingsRoutingModule,
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
    ComponentsModule,
    MaterialModule
  ],
  declarations: [
    SiteSettingsComponent
  ],
})
export class SiteSettingsModule { }
