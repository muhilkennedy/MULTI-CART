import { environment } from "src/environments/environment";
import { BaseEntity } from "./baseentity.model";

export class Tenant extends BaseEntity {

    private _tenantId: string;
    private _tenantName: string;
    private _tenantActive: boolean;
    private _locale: string;
    //tenant details
  
    constructor() {
        super();
        this._tenantId = environment.tenantId;
    }
  
    set tenantId(id:string){
      this._tenantId = id;
    }
  
    get tenantId():string{
      return this._tenantId;
    }
  
    set tenantName(name:string){
      this._tenantName = name;
    }
  
    get tenantName():string{
      return this._tenantName;
    }
  
    set tenantActive(active:boolean){
      this._tenantActive = active;
    }
  
    get tenantActive():boolean{
      return this._tenantActive;
    }

    set locale(loc:string){
      this._locale = loc;
    }
  
    get locale():string{
      return this._locale;
    }
    
}