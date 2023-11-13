import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class CommonUtil {

    static isNullOrEmptyOrUndefined(value: any) {
        return (value == undefined || value == null || value == '');
    }

}