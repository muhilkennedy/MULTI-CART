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
import { AuditLogsRouter } from './audit-log-routing.module';
import { AuditLogComponent } from './audit-log/audit-log.component';
import { TenantLogComponent } from './tenant-log/tenant-log.component';
import { ProductLogComponent } from './product-log/product-log.component';
import { ComponentsModule } from "../../../common-components/components.module";

@NgModule({
    declarations: [
        AuditLogComponent,
        TenantLogComponent,
        ProductLogComponent
    ],
    imports: [
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
        AlertModule,
        AuditLogsRouter,
        ComponentsModule
    ]
})
export class AuditLogsModule { }
