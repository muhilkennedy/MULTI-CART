import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import { SupplierService } from 'src/app/service/supplier/supplier.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';

@Component({
  selector: 'app-onboard-supplier',
  templateUrl: './onboard-supplier.component.html',
  styleUrls: ['./onboard-supplier.component.scss']
})
export class OnboardSupplierComponent implements OnInit {

  constructor(private _formBuilder: FormBuilder, private translate: TranslatePipe, private notificationService: NotificationService,
    private spinner: SpinnerService, private supplierService: SupplierService) {

  }

  basicFormGroup: any = this._formBuilder.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    address: ['', [Validators.required]]
  });

  contactFormGroup: any = this._formBuilder.group({
    emailId: ['', [Validators.required, Validators.email]],
    contact: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern("^[0-9]+$")]],
    secondaryContact: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern("^[0-9]+$")]]
  });

  public hasError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.hasFormFieldError(fieldGroup, fieldName);
  }

  public getError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.getFieldError(fieldGroup, fieldName, this.translate);
  }

  ngOnInit(): void {

  }

  onBoardSupplier() {
    this.spinner.show();
    let body = {
      name: this.basicFormGroup.controls['name'].value,
      description: this.basicFormGroup.controls['description'].value,
      address: this.basicFormGroup.controls['address'].value,
      emailId: this.contactFormGroup.controls['emailId'].value,
      contact: this.contactFormGroup.controls['contact'].value,
      secondarycontact: this.contactFormGroup.controls['secondaryContact'].value
    }
    this.supplierService.createSupplier(body)
      .subscribe({
        next: (resp: any) => {
          this.notificationService.fireAndForget(CommonUtil.generateSimpleNotificationMessage("New Supplier Onboarded!"), NotificationType.INFO);
        },
        error: (resp: any) => {
          this.notificationService.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(resp));
          this.spinner.hide();
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

}
