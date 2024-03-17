import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ProductService } from 'src/app/service/product/product.service';
import { SupplierService } from 'src/app/service/supplier/supplier.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {

  basicFormGroup: any = this._formBuilder.group({
    name: ['', Validators.required],
    description: [],
    measurement: ['', Validators.required],
    category: ['', Validators.required],
    categorySearchName: ['', Validators.required],
    supplier: []
  });

  loadCategory: boolean = false;
  allCategories!: any[];
  allSuppliers!: any[];
  allMeasurements!: any[];
  categorySearchText: string = '';
  selectedCategory: any;

  createdProduct: any = {};

  constructor(private productService: ProductService, private _formBuilder: FormBuilder, private supplierService: SupplierService,
    private notification: NotificationService, private spinner: SpinnerService) {

  }

  ngOnInit(): void {
    this.loadAllMeasurements();
    this.loadAllSuppliers();
  }

  loadAllMeasurements() {
    this.spinner.show();
    this.productService.getMeasurements()
      .subscribe({
        next: (resp: any) => {
          this.allMeasurements = resp.dataList;
        },
        error: (err: any) => {

        },
        complete: () => {
          this.spinner.hide();
        }
      });
  }

  //TODO: make this as type ahead as well
  loadAllSuppliers() {
    this.spinner.show();
    this.supplierService.getAllSupplier()
      .subscribe({
        next: (resp: any) => {
          this.allSuppliers = resp.dataList;
        },
        error: (err: any) => {

        },
        complete: () => {
          this.spinner.hide();
        }
      });
  }

  searchCategory() {
    if (!CommonUtil.isNullOrEmptyOrUndefined(this.basicFormGroup.controls['categorySearchName'].value) 
        && this.basicFormGroup.controls['categorySearchName'].value.length > 3
        && (this.categorySearchText != this.basicFormGroup.controls['categorySearchName'].value)) {
        this.categorySearchText = this.basicFormGroup.controls['categorySearchName'].value
        this.searchCategories(this.categorySearchText);
    }
  }

  searchCategories(searchText: string){
    this.loadCategory = true;
    this.productService.searchCategory(searchText)
      .subscribe({
        next: (resp: any) => {
          this.allCategories = resp.dataList;
        },
        error: (err: any) => {

        },
        complete: () => {
          this.loadCategory = false;
        }
      });
  }

  selectCategory(category: any){
    this.selectedCategory = category;
  }

  searchProduct() {

  }

  createProduct() {
    let body = {
      name : this.basicFormGroup.controls['name'].value,
      description: this.basicFormGroup.controls['description'].value,
      measurement: this.basicFormGroup.controls['measurement'].value,
      categoryId: this.selectedCategory.rootid,
      supplierId: this.basicFormGroup.controls['supplier'].value.rootid
    } 
    this.spinner.show();
    this.productService.createProduct(body)
        .subscribe({
          next: (resp: any) => {
            this.createdProduct = resp.data;
          },
          error: (err: any) => {

          },
          complete: () => {
            this.spinner.hide();
          }
        });
  }

  getSupplierName(){
    if(CommonUtil.isNullOrEmptyOrUndefined(this.basicFormGroup.controls['supplier'].value)){
      return '';
    }
    else{
      return this.basicFormGroup.controls['supplier'].value.name;
    }
  }

}
