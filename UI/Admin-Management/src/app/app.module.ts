import { APP_INITIALIZER, CUSTOM_ELEMENTS_SCHEMA, Injectable, LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule, DatePipe, HashLocationStrategy, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { BrowserModule, Title } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { HttpInterceptorService } from "./service/HttpInterceptor/http-interceptor.service";

import { NgScrollbarModule } from 'ngx-scrollbar';

// Import routing module
import { AppRoutingModule } from './app-routing.module';

// Import app component
import { AppComponent } from './app.component';

// Import containers
import { DefaultFooterComponent, DefaultHeaderComponent, DefaultLayoutComponent } from './containers';

import {
  AvatarModule,
  BadgeModule,
  BreadcrumbModule,
  ButtonGroupModule,
  ButtonModule,
  CalloutModule,
  CardModule,
  DropdownModule,
  FooterModule,
  FormModule,
  GridModule,
  HeaderModule,
  ListGroupModule,
  ModalModule,
  NavModule,
  ProgressModule,
  SharedModule,
  SidebarModule,
  SpinnerModule,
  TabsModule,
  UtilitiesModule
} from '@coreui/angular';

import { IconModule, IconSetService } from '@coreui/icons-angular';
import { Router, RouterModule } from '@angular/router';
import { ComponentsModule } from 'src/common-components/components.module';
import { I18nModule } from 'src/app/i18n/i18n.module';
import { environment } from 'src/environments/environment';
import { CookieService } from 'ngx-cookie-service';
import { TenantService } from './service/Tenant/tenant.service';
import { TranslateModule, TranslatePipe } from '@ngx-translate/core';
import { MaterialModule } from './material.module';
import { LoginComponent } from './views/login/login.component';
import { ResetPasswordComponent } from './views/reset-password/reset-password.component';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireMessagingModule } from '@angular/fire/compat/messaging';
// import { AngularFireModule, FirebaseApp } from '@angular/fire/compat';

const APP_CONTAINERS = [
  DefaultFooterComponent,
  DefaultHeaderComponent,
  DefaultLayoutComponent
];

@Injectable()
export class TenantInitializer {

  constructor(private router: Router, private http: HttpClient, private tenantService: TenantService) { }

  initializeApp(): Promise<any> {
    // const app = initializeApp(environment.firebaseConfig);
    // const msg = getMessaging(app);
    return new Promise((resolve, reject) => {
          console.log(`initializeApp:: Setting up Tenant`);
          this.http.get(`${environment.backendProxy}/tenant/ping`)
          .subscribe({
            next: (resp: any) => {
                  this.tenantService.getCurrentTenant().tenantId = resp.data.uniquename;
                  this.tenantService.getCurrentTenant().rootId = resp.data.rootid;
                  this.tenantService.getCurrentTenant().tenantActive = resp.data.active;
                  this.tenantService.getCurrentTenant().tenantName = resp.data.tenantName;
                  this.tenantService.getCurrentTenant().locale = resp.data.locale;
                  this.tenantService.getCurrentTenant().details = resp.data.tenantDetail;
                  //load app only if tenant is active.
                  if(this.tenantService.getCurrentTenant().tenantActive){
                     resolve(true);
                  }
                  else{
                    resolve(false);
                    this.router.navigate(['/error', { message : 'Tenant is Deactivated/Expired!'}]);
                  }
            },
            error: (error) => {
              console.log("Server Not Reachable");
              resolve(false);
              this.router.navigate(['/error', { message : 'Server is not Reachable!'}]);
            },
            complete: ()=>{
              console.log("---- Page Loaded ----");
            }
          })
    });
  }
}

export function init_tenant(initializer: TenantInitializer) {
  return () => initializer.initializeApp();
}

@NgModule({
  declarations: [AppComponent, ...APP_CONTAINERS, LoginComponent, ResetPasswordComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AvatarModule,
    BreadcrumbModule,
    FooterModule,
    DropdownModule,
    GridModule,
    HeaderModule,
    SidebarModule,
    IconModule,
    NavModule,
    ButtonModule,
    FormModule,
    UtilitiesModule,
    ButtonGroupModule,
    ReactiveFormsModule,
    SidebarModule,
    SharedModule,
    TabsModule,
    ListGroupModule,
    ProgressModule,
    BadgeModule,
    ListGroupModule,
    CardModule,
    NgScrollbarModule,
    RouterModule,
    CommonModule,
    HttpClientModule,
    //additinal modules
    ComponentsModule,
    I18nModule,
    MaterialModule,
    TranslateModule,
    SpinnerModule,
    UtilitiesModule,
    FormsModule,
    CommonModule ,
    CalloutModule,
    ModalModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireMessagingModule,
  ],
  exports: [
    
  ],
  providers: [
    TenantInitializer,
    {
      provide: APP_INITIALIZER,
      useFactory: init_tenant,
      multi: true,
      deps: [TenantInitializer]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    },
    {
      provide: LOCALE_ID,
      useValue: 'en'
    },
    CookieService,
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    },
    IconSetService,
    Title,
    TranslatePipe,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
