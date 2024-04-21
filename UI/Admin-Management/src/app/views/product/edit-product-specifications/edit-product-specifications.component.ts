import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ProductService } from 'src/app/service/product/product.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-edit-product-specifications',
  templateUrl: './edit-product-specifications.component.html',
  styleUrls: ['./edit-product-specifications.component.scss']
})
export class EditProductSpecificationsComponent implements OnInit {
  
  @Input('product') product: any;
  @Input('productInfo') productInfo: any;
  @Output() editProductSpecs = new EventEmitter<boolean>();

  model!: string;
  weight!: string;
  dimensions!: string;
  colour!: string;
  description!: string;
  origin!: string;
  manufacturer!: string;
  details: prodDetail[] = new Array();

  constructor(private productService: ProductService, private spinner: SpinnerService,
              private notification: NotificationService){
    this.details.push(new prodDetail());
  }
  
  ngOnInit(): void {
    this.spinner.show();
    this.productService.getProductSpecs(this.productInfo.rootid)
        .subscribe({
          next: (resp: any) => {
            this.model = resp.data.specifications.model;
            this.weight = resp.data.specifications.weight;
            this.dimensions = resp.data.specifications.dimensions;
            this.colour = resp.data.specifications.colour;
            this.origin = resp.data.specifications.origin;
            this.manufacturer = resp.data.specifications.manufacturer;
            let json = JSON.parse(resp.data.specifications.details);
            if(!CommonUtil.isNullOrEmptyOrUndefined(resp.data.specifications.details)){
              this.details.length= 0;
              for (let key in json) {
                let pdetail: prodDetail = new prodDetail();
                pdetail.key = key;
                pdetail.value = json[key];
                this.details.push(pdetail);
              }
              //this.details = resp.data.specifications.details;
            }
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          },
          complete: () =>{
            this.spinner.hide();
          }
        })
  }

  addNewField(){
    this.details.push(new prodDetail());
  }

  removeField(index: number){
    this.details.splice(index, 1);
  }

  backAction(){
    this.editProductSpecs.emit(true);
  }

  updateProductSepcs(){
    this.spinner.show();
    let map: Map<string, string> = new Map<string, string>();
    let json: any = {};
    this.details.forEach(detail => {
      //map.set(detail.key,detail.value);
      json[detail.key] = detail.value;
    });
    let body = {
      colour: this.colour,
      model: this.model,
      weight: this.weight,
      dimensions: this.dimensions,
      origin: this.origin,
      manufacturer: this.manufacturer,
      details: json
    }
    this.productService.updateProductSpecs(this.productInfo.rootid, body)
        .subscribe({
          next: (resp: any) => {
            this.notification.fireAndForget(CommonUtil.generateSimpleNotificationMessage("Updated Product Specifications Successfully!"), NotificationType.PRIMARY);
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          },
          complete: () =>{
            this.spinner.hide();
          }
        })
  }

}

export class prodDetail {

  public key!: string;
  public value!: string;

}
