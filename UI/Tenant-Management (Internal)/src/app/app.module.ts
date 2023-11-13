import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { APP_INITIALIZER, Injectable, LOCALE_ID, NgModule } from "@angular/core";
import { registerLocaleData } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { Router, RouterModule } from "@angular/router";
import { ToastrModule } from 'ngx-toastr';

import { AppComponent } from "./app.component";
import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { AppRoutingModule } from "./app-routing.module";
import { ComponentsModule } from "./components/components.module";
import { TenantService } from "./service/Tenant/tenant.service";
import { HttpInterceptorService } from "./service/HttpInterceptor/http-interceptor.service";
import { LoginComponent } from "./pages/login/login.component";
import { CommonModule } from "@angular/common";
import { CookieService } from "ngx-cookie-service";
import { MaterialModule} from './material.module';
import { I18nModule } from './i18n/i18n.module';
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { environment } from "src/environments/environment";
import { ErrorComponent } from "./pages/error/error.component";
import { NotFound } from "./pages/notfound/notfound.component";

@Injectable()
export class TenantInitializer {

  constructor(private http: HttpClient, private tenantService: TenantService, private router: Router) { }

  initializeApp(): Promise<any> {
    return new Promise((resolve, reject) => {
          console.log(`initializeApp:: Setting up Tenant`);
          this.http.get(`${environment.backendProxy}/tenant/ping`)
          .subscribe({
            next: (resp: any) => {
                  this.tenantService.getCurrentTenant().tenantId = resp.data.tenantId;
                  this.tenantService.getCurrentTenant().rootId = resp.data.rootId;
                  this.tenantService.getCurrentTenant().tenantActive = resp.data.active;
                  this.tenantService.getCurrentTenant().tenantName = resp.data.tenantName;
                  this.tenantService.getCurrentTenant().locale = resp.data.locale;
                  //load app only if tenant is active.
                  if(this.tenantService.getCurrentTenant().tenantActive){
                     resolve(true);
                  }
                  else{
                    alert("Tenant not Active! Please contact support!")
                  }
            },
            error: (error) => {
              console.log("Server Not Reachable");
              resolve(false);
              this.router.navigate(['/error']);
            //   this.toastr.error(` <div class='container'>
            //   <div class='tear'></div>
            //   <div class='tear2'></div>
            //   <div class='face'>
            //       <div class='eyebrow'>︶</div>
            //       <div class='eyebrow'>︶</div>
            //       <div class='eye'></div>
            //       <div class='eye'></div>
            //       <div class='mouth'></div>
            //   </div>
            // </div> <span class="tim-icons icon-bell-55" [data-notify]="icon"></span> <b> Server Not Reachable </b>`, '', {
            //     disableTimeOut: true,
            //     closeButton: true,
            //     enableHtml: true,
            //     toastClass: "alert alert-error alert-with-icon",
            //     positionClass: 'toast-' + 'bottom' + '-' + 'center'
            //   });
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
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ComponentsModule,
    NgbModule,
    RouterModule,
    AppRoutingModule,
    ReactiveFormsModule,
    CommonModule,
    ToastrModule.forRoot(),
    MaterialModule,
    I18nModule,
    TranslateModule
  ],
  declarations: [AppComponent, AdminLayoutComponent, LoginComponent, ErrorComponent, NotFound],
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
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
