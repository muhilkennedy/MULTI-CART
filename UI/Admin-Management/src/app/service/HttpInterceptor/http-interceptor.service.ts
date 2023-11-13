import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable, catchError, of, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TenantService } from '../Tenant/tenant.service';
import { CommonUtil } from '../util/common-util.service';
import { Router } from '@angular/router';
import { SpinnerService } from '../util/sipnner.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private tenantService: TenantService, private cookieService: CookieService, private router: Router) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let newHeaders = req.headers;
    // Append tenant-Id and token to all outgoing requests.
    if (environment.tenantId) {
      newHeaders = newHeaders.append('X-Tenant', environment.tenantId);
      newHeaders = newHeaders.append('Accept-Language', CommonUtil.isNullOrEmptyOrUndefined(this.cookieService.get("lang")) ? this.cookieService.get("lang") : "en");
      newHeaders = newHeaders.append('Authorization', 'Bearer ' + this.cookieService.get("X-Token"));
    }
    const authReq = req.clone({ headers: newHeaders });
    //next.handle(authReq)
    return next.handle(authReq).pipe(tap(() => {},
      (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status !== 401) {
         return;
        }
        this.router.navigate(['/login', { message : 'Unauthorized Access! Please Login Again!'}]);
      }
    }, () => {
       //complete action
    }));
  }
}
