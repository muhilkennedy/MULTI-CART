import { PosProduct } from "./PosProduct.model";

export class Invoice {

    invoiceId!: string;
    billingUser!: string;
    products: PosProduct[] = new Array();
    customerMobile!: string;
    customerName!: string;
    totalPrice!: number;
    totalDiscount!: number;
    actualTotal!: number;
    paymentMode!: string;

    tenantAddress!: string;
    tenantContact!: string;
    tenantEmail!: string;
}