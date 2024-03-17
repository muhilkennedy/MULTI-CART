import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CommonUtil } from '../util/common-util.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getCategories(): Observable<any> {
    return this.http.get(`${environment.productProxy}/category`);
  }

  getMeasurements(): Observable<any> {
    return this.http.get(`${environment.productProxy}/admin/product/measurements`);
  }

  createCategory(body: any): Observable<any> {
    return this.http.post(`${environment.productProxy}/admin/category`, body);
  }

  deleteCategory(id: any): Observable<any> {
    return this.http.delete(`${environment.productProxy}/admin/category/${id}`);
  }

  searchCategory(searchText: string): Observable<any> {
    return this.http.get(`${environment.productProxy}/category/search/${searchText}`);
  }

  createProduct(body: any): Observable<any> {
    return this.http.post(`${environment.productProxy}/admin/product`, body);
  }

  createOrUpdateProductInventory(body: any): Observable<any> {
    return this.http.post(`${environment.productProxy}/admin/product/inventory`, body);
  }

  addProductInfo(body: any): Observable<any> {
    return this.http.post(`${environment.productProxy}/admin/product/info`, body);
  }

  updateProductImage(productId: any, infoId: any, image: File): Observable<any> {
    const formData = new FormData();
    formData.append("infoId", infoId);
    formData.append("file", image, image.name);
    return this.http.post<any>(`${environment.productProxy}/admin/product/image/${productId}`, formData)
  }

  getProduct(id: any): Observable<any> {
    return this.http.get(`${environment.productProxy}/product/${id}`);
  }

  getProductByBarcode(barcode: any): Observable<any> {
    return this.http.get(`${environment.productProxy}/admin/product/search/${barcode}`);
  }

  getCompleteProductDetails(id: any): Observable<any> {
    return this.http.get(`${environment.productProxy}/admin/product/${id}`);
  }

  getProductInventory(id: any): Observable<any> {
    return this.http.get(`${environment.productProxy}/admin/product/inventory/${id}`);
  }

  getProducts(pageIndex: number, pageSize: number, includeInActive: boolean, supplierId: number, categoryId: number): Observable<any> {
    let queryParams: any = {
      pageNumber: pageIndex,
      pageSize: pageSize,
      includeInactive: includeInActive,
    }
    if(!CommonUtil.isNullOrEmptyOrUndefined(supplierId)){
      queryParams.supplierId = supplierId;
    }
    if(!CommonUtil.isNullOrEmptyOrUndefined(categoryId)){
      queryParams.categoryId = categoryId;
    }
    return this.http.get(`${environment.productProxy}/admin/product`, {
      params: queryParams
    });
  }

}
