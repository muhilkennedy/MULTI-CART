import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TenantService } from '../Tenant/tenant.service';
import { CommonUtil } from '../util/common-util.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private cookieService: CookieService, private tenantService: TenantService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //let url = environment.backendBaseUrl + req.url;
    //if (req.url.includes("/assets/i18n/")){
    //url = req.url;
    //}
    let newHeaders = req.headers;
    // Append tenant-Id and token to all outgoing requests.
    if (environment.tenantId) {
      newHeaders = newHeaders.append('X-Tenant', environment.tenantId);
      newHeaders = newHeaders.append('Accept-Language', CommonUtil.isNullOrEmptyOrUndefined(this.tenantService.getCurrentTenant().locale) ? 'en_US' : this.tenantService.getCurrentTenant().locale);
      newHeaders = newHeaders.append('Authorization', 'Bearer ' + this.cookieService.get("X-Token"));
    }
    const authReq = req.clone({ headers: newHeaders });
    return next.handle(authReq);
    // return next.handle(authReq).pipe(
    //   catchError((error) => {
    //     console.log('error is intercept')
    //     console.error(error);
    //     return throwError(error);
    //   })
    // )
  }
}
