import { Injectable } from "@angular/core";
import { TranslatePipe } from "@ngx-translate/core";

@Injectable({
    providedIn: 'root'
})
export class CommonUtil {

    static TOKEN_KEY = 'X-Token';
    static DATE_FORMAT_PLAIN = "dd/MM/yyyy";

    static isNullOrEmptyOrUndefined(value: any): boolean {
        return (value == undefined || value == null || value == '');
    }

    static hasFormFieldError(fieldGroup: any, fieldName: string): boolean {
        return fieldGroup.controls[fieldName].status == "INVALID";
    }

    static generateErrorNotificationFromResponse(err: any) {
        if (this.isNullOrEmptyOrUndefined(err.error.errorCode)) {
            return { title: `${this.isNullOrEmptyOrUndefined(err.error.title) ? err.error.error : err.error.title}(${err.error.status})`, 
                    message: this.isNullOrEmptyOrUndefined(err.error.detail) ? err.error.message : err.error.detail };
        }
        return { title: `${err.error.errorCode}(${err.error.status})`, message: err.error.message };
    }

    static getFieldError(fieldGroup: any, fieldName: string, translate: TranslatePipe): Array<string> {
        let errorList = new Array();
        if (!this.isNullOrEmptyOrUndefined(fieldGroup.controls[fieldName]) && !this.isNullOrEmptyOrUndefined(fieldGroup.controls[fieldName].errors)) {
            if (fieldGroup.controls[fieldName].errors['required']) {
                errorList.push(translate.transform("This field cannot be empty"));
            }
            if (fieldGroup.controls[fieldName].errors['minlength']) {
                errorList.push(translate.transform("Minimum length ") + fieldGroup.controls[fieldName].errors['minlength'].requiredLength);
            }
            if (fieldGroup.controls[fieldName].errors['maxlength']) {
                errorList.push(translate.transform("Maximum length ") + fieldGroup.controls[fieldName].errors['maxlength'].requiredLength);
            }
            if (fieldGroup.controls[fieldName].errors['email']) {
                errorList.push(translate.transform("Please enter a valid emailId"));
            }
            if (fieldGroup.controls[fieldName].errors['pattern']) {
                errorList.push(translate.transform("Invalid value entered for this field"));
            }
            return errorList;
        }
        return errorList;
    }

}

export enum Colors {
    '' = '',
    primary = 'primary',
    secondary = 'secondary',
    success = 'success',
    info = 'info',
    warning = 'warning',
    danger = 'danger',
    dark = 'dark',
    light = 'light',
}