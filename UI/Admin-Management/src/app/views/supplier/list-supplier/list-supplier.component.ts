import { Component, OnInit } from '@angular/core';
import { SupplierService } from 'src/app/service/supplier/supplier.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-list-supplier',
  templateUrl: './list-supplier.component.html',
  styleUrls: ['./list-supplier.component.scss']
})
export class ListSupplierComponent implements OnInit {

  displayedRolesColumns: string[] = ['id', 'name', 'status', 'emailid', 'contact', 'secondarycontact'];
  suppliers: any[] = new Array();

  constructor(private supplier: SupplierService, private spinner: SpinnerService, private notification: NotificationService) {

  }

  ngOnInit(): void {
    this.getAllSuppliers();
  }

  getAllSuppliers() {
    this.spinner.show();
    this.supplier.getAllSupplier()
      .subscribe({
        next: (resp: any) => {
          this.suppliers = resp.dataList;
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

  toogleSupplierStatus(rootId: any) {
    this.spinner.show();
  }

}
