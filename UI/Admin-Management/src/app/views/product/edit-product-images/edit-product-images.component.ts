import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ProductService } from 'src/app/service/product/product.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-edit-product-images',
  templateUrl: './edit-product-images.component.html',
  styleUrls: ['./edit-product-images.component.scss']
})
export class EditProductImagesComponent implements OnInit{

  @Input('product') product: any;
  @Input('productInfo') productInfo: any;
  @Output() editProductImages = new EventEmitter<boolean>();

  constructor(private productService: ProductService, private spinner: SpinnerService,
              private notification: NotificationService){

  }

  ngOnInit(): void {
    
  }

  backAction(){
    this.editProductImages.emit(true);
  }


}
