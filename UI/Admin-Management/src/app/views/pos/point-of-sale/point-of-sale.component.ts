import { Component, OnInit } from '@angular/core';
import { PdfUtil } from 'src/app/service/util/pdf.service';
import { PosProduct } from '../../../model/PosProduct.model';
import { Invoice } from '../../../model/invoice.model';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { ProductService } from 'src/app/service/product/product.service';
import { TenantService } from 'src/app/service/Tenant/tenant.service';

@Component({
  selector: 'app-point-of-sale',
  templateUrl: './point-of-sale.component.html',
  styleUrls: ['./point-of-sale.component.scss']
})
export class PointOfSaleComponent implements OnInit {

  paymentModes: string[] = ["CASH", "CARD", "PAYTM", "GPAY", "PHONEPE", "SAMSUNG PAY", "UPI", "OTHERS"];
  productList: PosProduct[] = new Array();
  actualTotal: number = 0;
  totalPrice: number = 0;
  totalDiscount: number = 0;
  invoice: Invoice = new Invoice();

  currentBarcode: string = '';
  paymentMode: any;

  constructor(private userService: UserService, private spinner: SpinnerService, private notification: NotificationService,
              private productService: ProductService, private tenantService: TenantService) {
    this.productList.push(new PosProduct());
  }

  ngOnInit(): void {

  }

  addNewProduct() {
    this.productList.unshift(new PosProduct());
  }

  removeProduct(index: number) {
    this.productList.splice(index, 1);
  }

  getFinalPrice(product: PosProduct) {
    return (product.mrp - (product.mrp * (product.discount / 100))) * product.quantity;
  }

  getTotalDiscount() {
    // let discount = 0;
    // this.productList.forEach(element => {
    //   discount += ((element.mrp * (element.discount / 100)) * element.quantity);
    // });
    // return discount;
    return this.getActualTotalPrice() - this.getTotalPrice();
  }

  getTotalPrice() {
    let price = 0;
    this.productList.forEach(element => {
      if(!CommonUtil.isNullOrEmptyOrUndefined(element.price)){
        price += (element.price * element.quantity);
      }
    });
    return price;
  }

  getActualTotalPrice() {
    let mrp = 0;
    this.productList.forEach(element => {
      mrp += element.mrp * element.quantity;
    });
    return mrp;
  }

  payBill(){
    //TODO: API to generate invoiceid
    this.generateInvoiceBill();
  }

  generateInvoiceBill() {
    this.invoice.products = this.productList.filter(product => !CommonUtil.isNullOrEmptyOrUndefined(product.productName));
    this.invoice.invoiceId = "1234567";
    this.invoice.billingUser = this.userService.getCurrentUserResponse().uniquename;
    this.invoice.customerName = "Muhil";
    this.invoice.customerMobile = "7200291880";
    this.invoice.tenantAddress = this.tenantService.getCurrentTenant().details.street + ", " + 
                                  this.tenantService.getCurrentTenant().details.city + ", " + 
                                  this.tenantService.getCurrentTenant().details.pincode ;
    this.invoice.tenantContact = this.tenantService.getCurrentTenant().details.contact;
    this.invoice.tenantEmail = this.tenantService.getCurrentTenant().details.emailid;
    if(CommonUtil.isNullOrEmptyOrUndefined(this.invoice.paymentMode)){
      //take default payment mode
      this.invoice.paymentMode = this.paymentModes[0];
    }
    PdfUtil.generatePDF(this.invoice);
  }

  getProductByBarCode(barcode: any){
    if(!CommonUtil.isNullOrEmptyOrUndefined(barcode) && barcode.length > 3){
      this.spinner.show();
      this.productService.getPOSProductByBarcode(barcode)
          .subscribe({
            next: (resp: any) => {
              let posProd: PosProduct  = new PosProduct();
              posProd.barCode = resp.data.barcode;
              posProd.mrp = resp.data.productinfo.mrp;
              posProd.price = resp.data.productinfo.price;
              posProd.productName = resp.data.productinfo.product.name;
              posProd.discount = resp.data.productinfo.discount;
              posProd.quantity = 1;
              let index: number = this.getProductIndexIfPresent(posProd);
              if(index < 0){
                this.productList.push(posProd);
              }
              else{
                this.productList[index].quantity++;
              }
              this.productList[0].barCode='';
            },
            error: (err : any) => {
              this.notification.fireAndForget(CommonUtil.generateErrorNotificationFromResponse(err), NotificationType.WARNING);
              this.spinner.hide();
            },
            complete: () =>{ 
              this.spinner.hide();
            }
          })
    }
  }

  changePaymentMode(mode: any){
    this.invoice.paymentMode = mode;
  }

  getProductIndexIfPresent(posProd: PosProduct): number{
    for(let index = 1; index< this.productList.length; index++){
      if(this.productList[index].barCode == posProd.barCode)
        return index;
    }
    return -1;
  }

}
