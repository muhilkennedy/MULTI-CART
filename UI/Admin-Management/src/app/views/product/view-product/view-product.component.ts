import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';
import { ProductService } from 'src/app/service/product/product.service';
import { SupplierService } from 'src/app/service/supplier/supplier.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-view-product',
  templateUrl: './view-product.component.html',
  styleUrls: ['./view-product.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class ViewProductComponent implements OnInit {

  @Input("edit") canEdit: boolean = false;
 
  filterFormGroup: any = this._formBuilder.group({
    name: [''],
    slider: [50],
    includeInactive: false,
    categorySearchName: [''],
    supplier: [],
    expired: false,
    outOfStock: false
  });

  loadCategory: boolean = false;
  allCategories!: any[];
  allSuppliers!: any[];
  categorySearchText: string = '';
  selectedCategory: any = null;

  nameFilter!: string;
  includeInActive: boolean = false;
  barcode!: string;

  displayedColumns = ['id', 'name', 'rating', 'status', 'actions'];
  products!:any[];
  expandedElement: any | null;
  expandAll: boolean = false;

  totalPages: number = 10;
  pageSize: number = 25;
  pageIndex: number = 0;
  sortBy: string = '';
  sortOrder: string = '';

  constructor(private translate: TranslatePipe, private _formBuilder: FormBuilder, private notification: NotificationService,
    private spinner: SpinnerService, private productService: ProductService, private supplierService: SupplierService,
    private router: Router){

  }

  ngOnInit(): void {
    this.loadAllSuppliers();
    this.loadProducts();
  }

  handlePageEvent(e: PageEvent) {
    this.totalPages = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.loadProducts();
  }

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

  formatLabel(value: number): string {
    if (value >= 1000) {
      return Math.round(value / 1000) + 'k';
    }
    return `${value}`;
  }

  applyFilter(){
    this.loadProducts();
  }

  clearFilter(){
    this.selectedCategory = null;
    this.filterFormGroup.controls['supplier'].setValue(null);
    this.filterFormGroup.controls['includeInactive'].setValue(false);
    this.filterFormGroup.controls['categorySearchName'].setValue(null);
    this.categorySearchText = '';
    this.includeInActive = false;
    this.barcode = '';
    this.applyFilter();
  }

  searchByName(){
    if(this.filterFormGroup.controls['name'].value.length > 3){
      this.pageIndex = 0;
      this.totalPages = 0;
      this.spinner.show();
    }
  }

  getSupplierName(){
    if(CommonUtil.isNullOrEmptyOrUndefined(this.filterFormGroup.controls['supplier'].value)){
      return '';
    }
    else{
      return this.filterFormGroup.controls['supplier'].value.name;
    }
  }

  searchCategory() {
    if (!CommonUtil.isNullOrEmptyOrUndefined(this.filterFormGroup.controls['categorySearchName'].value) 
        && this.filterFormGroup.controls['categorySearchName'].value.length > 3
        && (this.categorySearchText != this.filterFormGroup.controls['categorySearchName'].value)) {
        this.categorySearchText = this.filterFormGroup.controls['categorySearchName'].value
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
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.loadCategory = false;
        }
      });
  }

  selectCategory(category: any){
    this.selectedCategory = category;
  }

  openProductInfo(){
    this.router.navigate(['/productinfo']);
  }

  loadProducts(){
    this.spinner.show();
    let supplierId = !CommonUtil.isNullOrEmptyOrUndefined(this.filterFormGroup.controls['supplier'].value) ? this.filterFormGroup.controls['supplier'].value.rootid : null;
    let categoryId = !CommonUtil.isNullOrEmptyOrUndefined(this.selectedCategory) ? this.selectedCategory.rootid : null;
    let includeInactive = this.filterFormGroup.controls['includeInactive'].value;
    this.productService.getProducts(this.pageIndex, this.pageSize, includeInactive, supplierId, categoryId)
        .subscribe({
          next: (resp: any) => {
            this.products = resp.data.content;
            this.totalPages = resp.data.totalElements;
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          },
          complete: ()=>{
            this.spinner.hide();
          }
        })
  }

  searchByBarcode(){
      this.spinner.show();
      this.productService.getProductByBarcode(this.barcode)
          .subscribe({
            next: (resp: any) => {
              let prod: any[] = new Array();
              prod.push(resp.data);
              this.products = prod
              this.totalPages = 0;
              this.expandedElement = resp.data;
            },
            error: (err: any) => {
              this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
            },
            complete: ()=>{
              this.spinner.hide();
            }
          })
  }

  expandAction(event: any, element: any){
    this.expandedElement = this.expandedElement === element ? null : element;
    //event.stopPropagation();
  }

  expandAllAction(){
    this.expandAll = !this.expandAll;
  }

  toggleProductStatus(product: any){
    this.spinner.show();
  }
  
  getStatusBadge(element: any): string{
    return element.active? "Active" : "Deactivated"
  }

}
