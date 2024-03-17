import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { ProductService } from 'src/app/service/product/product.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-product-info',
  templateUrl: './product-info.component.html',
  styleUrls: ['./product-info.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class ProductInfoComponent implements OnInit {

  @Input('product') product: any;
  @Input('edit') edit: boolean = true;
  @Input('markBarcode') highlightBarcode: string = '';

  displayedColumns = ['size', 'mrp', 'price', 'discount', 'actions'];
  expandedElement: any | null;
  showInventoryModal = false;
  showInfoModal = false;

  @Input('productInfos') productInfos: any[] = new Array();
  selectedPInfoId: number = -1;
  selectedImage!: File | any;

  infoFormGroup = this._formBuilder.group({
    psize: ['', Validators.required],
    mrp: ['', Validators.required],
    price: ['', Validators.required],
    discount: ['']
  });

  invFormGroup = this._formBuilder.group({
    infoId: ['', Validators.required],
    barcode: ['', Validators.required],
    expiry: ['NO EXPIRY'],
    quantity: ['', Validators.required]
  });

  constructor(private translate: TranslatePipe, private _formBuilder: FormBuilder, private notification: NotificationService,
    private spinner: SpinnerService, private productService: ProductService, private changeDetectorRefs: ChangeDetectorRef) {

  }

  public hasError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.hasFormFieldError(fieldGroup, fieldName);
  }

  public getError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.getFieldError(fieldGroup, fieldName, this.translate);
  }

  ngOnInit(): void {
    this.infoFormGroup.get('discount')?.disable();
    if(!CommonUtil.isNullOrEmptyOrUndefined(this.highlightBarcode)){
      //TODO: optimize
      this.productInfos.forEach(element => {
        element.inventory.forEach((inv: any) => {
          if(inv.barcode == this.highlightBarcode){
            this.expandedElement = element;
          }
        })
      });
    }
  }

  loadInventoryModal(productInfoId: any) {
    this.toggleInventoryModal();
  }

  toggleInventoryModal() {
    this.showInventoryModal = !this.showInventoryModal;
  }

  openInventoryModal(pinfoId: number) {
    this.selectedPInfoId = pinfoId;
    this.toggleInventoryModal();
  }

  handleInventoryModal(event: boolean) {
    this.showInventoryModal = event;
  }

  loadInfoModal(productInfoId: any) {
    this.toggleInfoModal();
  }

  toggleInfoModal() {
    this.showInfoModal = !this.showInfoModal;
  }

  handleInfoModal(event: boolean) {
    this.showInfoModal = event;
  }

  onPictureSeclected(event: any) {
    this.selectedImage = event.target.files[0];
  }

  addProductInfo() {
    let body = {
      productId: this.product.rootid,
      mrp: this.infoFormGroup.value.mrp,
      price: this.infoFormGroup.value.price,
      discount: this.infoFormGroup.value.discount,
      size: this.infoFormGroup.value.psize,
    }
    this.spinner.show();
    this.productService.addProductInfo(body)
      .subscribe({
        next: (resp: any) => {
          this.uploadProductPicture(this.product.rootid, resp.data.rootid);
        },
        error: (err: any) => {
          this.notification.fireAndWait(CommonUtil.generateErrorNotificationFromResponse(err), NotificationType.DANGER);
        },
        complete: () => {
          this.toggleInfoModal();
        }
      })
  }

  loadProduct(id: any){
    this.spinner.show();
    this.productService.getCompleteProductDetails(id)
        .subscribe({
          next: (resp: any) => {
            this.product = resp.data;
            this.productInfos = resp.data.infos;
          },
          error: (err: any) => {
            this.notification.fireAndWait(CommonUtil.generateErrorNotificationFromResponse(err), NotificationType.DANGER);
          },
          complete: () => {
            this.spinner.hide();
          }
        })
  }

  uploadProductPicture(productId:any, infoId: any){
    if(!CommonUtil.isNullOrEmptyOrUndefined(this.selectedImage)){
      this.productService.updateProductImage(productId,infoId, this.selectedImage)
          .subscribe({
            next: (resp: any) => {
              this.loadProduct(this.product.rootid);
              this.selectedImage = null;
            },
            error: (err: any) => {
              this.notification.fireAndWait(CommonUtil.generateErrorNotificationFromResponse(err), NotificationType.DANGER);
            },
            complete: () => {
            }
          })
    }
    else{
      this.loadProduct(this.product.rootid);
    }
  }

  updateProductInventory(){
    this.spinner.show();
    let body = {
      productInfoId: this.selectedPInfoId,
      barcode: this.invFormGroup.value.barcode,
      expiry: this.invFormGroup.value.expiry,
      availableQuantity: this.invFormGroup.value.quantity,
    }
    this.productService.createOrUpdateProductInventory(body)
        .subscribe({
          next: (resp: any) => {
            this.loadProduct(this.product.rootid);
          },
          error: (err: any) => {
            this.notification.fireAndWait(CommonUtil.generateErrorNotificationFromResponse(err), NotificationType.DANGER);
          },
          complete: () => {
            this.spinner.hide();
            //this.toggleInfoModal();
          }
        })
  }

  getDiscount(){
    if(!CommonUtil.isNullOrEmptyOrUndefined(this.infoFormGroup.value.mrp) && !CommonUtil.isNullOrEmptyOrUndefined(this.infoFormGroup.value.price)){
      let mrp = Number(this.infoFormGroup.value.mrp);
      let price = Number(this.infoFormGroup.value.price);
      let discount: any = Math.round(100 * ((mrp-price)/mrp));
      this.infoFormGroup.controls["discount"].setValue(discount);
      return discount;
    }
    return null;
  }

}
