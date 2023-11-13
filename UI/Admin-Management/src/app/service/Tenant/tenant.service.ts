import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tenant } from 'src/app/model/tenant.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TenantService {

  private tenant: Tenant;

  constructor(private http: HttpClient) {
    this.tenant = new Tenant();
  }

  getCurrentTenant(): Tenant {
    return this.tenant;
  }

}