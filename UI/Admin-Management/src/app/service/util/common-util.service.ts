import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class CommonUtil {

    static TOKEN_KEY = 'X-Token';

    static isNullOrEmptyOrUndefined(value: any): boolean {
        return (value == undefined || value == null || value == '');
    }

    static hasFormFieldError(fieldGroup: any, fieldName: string): boolean {
        return fieldGroup.controls[fieldName].status == "INVALID";
    }

    static generateErrorNotificationFromResponse(err: any) {
        if(this.isNullOrEmptyOrUndefined(err.error.errorCode)){
            return { title: `${err.error.title}(${err.error.status})`, message: err.error.detail };
        }
        return { title: `${err.error.errorCode}(${err.error.status})`, message: err.error.message };
    }

    static getFieldError(fieldGroup: any, fieldName: string): Array<string> {
        let errorList = new Array();
        if (!this.isNullOrEmptyOrUndefined(fieldGroup.controls[fieldName]) && !this.isNullOrEmptyOrUndefined(fieldGroup.controls[fieldName].errors)) {
            if (fieldGroup.controls[fieldName].errors['required']) {
                errorList.push("This field cannot be empty");
            }
            if (fieldGroup.controls[fieldName].errors['minlength']) {
                errorList.push("Minimum length " + fieldGroup.controls[fieldName].errors['minlength'].requiredLength);
            }
            if (fieldGroup.controls[fieldName].errors['maxlength']) {
                errorList.push("Maximum length " + fieldGroup.controls[fieldName].errors['maxlength'].requiredLength);
            }
            if (fieldGroup.controls[fieldName].errors['email']) {
                errorList.push("Please enter a valid emailId");
            }
            if (fieldGroup.controls[fieldName].errors['pattern']) {
                errorList.push("Invalid value entered for this field");
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