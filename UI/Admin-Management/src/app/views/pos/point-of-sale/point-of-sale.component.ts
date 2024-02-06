import { Component, OnInit } from '@angular/core';
import { PdfUtil } from 'src/app/service/util/pdf.service';
import { PosProduct } from '../../../model/PosProduct.model';
import { Invoice } from '../../../model/invoice.model';
import { UserService } from 'src/app/service/user/user.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';

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

  constructor(private userService: UserService) {
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
    let discount = 0;
    this.productList.forEach(element => {
      discount += ((element.mrp * (element.discount / 100)) * element.quantity);
    });
    return discount;
  }

  getTotalPrice() {
    let price = 0;
    this.productList.forEach(element => {
      price += this.getFinalPrice(element);
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
    this.invoice.billingUser = "E-123";//this.userService.getCurrentUser().uniqueName;
    this.invoice.customerName = "Muhil";
    this.invoice.customerMobile = "7200291880";
    this.invoice.tenantAddress = "thillaiyadi valli st, Mamallapuram";
    this.invoice.tenantContact = "89887876756";
    this.invoice.tenantEmail = "muhilkennedy@gmail.com"
    PdfUtil.generatePDF(this.invoice);
  }

}
