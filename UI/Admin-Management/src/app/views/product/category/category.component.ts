import { FlatTreeControl } from '@angular/cdk/tree';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material/tree';
import { TranslatePipe } from '@ngx-translate/core';
import { ProductService } from 'src/app/service/product/product.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService } from 'src/app/service/util/notification.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';

interface ItemNode {
  name: string;
  rootId: number;
  children?: ItemNode[];
}

/** Flat node with expandable and level information */
interface ItemFlatNode {
  expandable: boolean;
  name: string;
  rootId: number;
  level: number;
}

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  treeControl = new FlatTreeControl<ItemFlatNode>(
    node => node.level,
    node => node.expandable,
  );

  private _transformer = (node: ItemNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      rootId: node.rootId,
      level: level,
    };
  };

  treeFlattener = new MatTreeFlattener(
    this._transformer,
    node => node.level,
    node => node.expandable,
    node => node.children,
  );

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  hasChild = (_: number, node: ItemFlatNode) => node.expandable;

  showAddCategoryModal = false;
  selectedParentId = null;
  categoryFormGroup = this._formBuilder.group({
    categoryName: ['', Validators.required]
  });

  constructor(private productService: ProductService, private notification: NotificationService,
    private spinner: SpinnerService, private _formBuilder: FormBuilder, private translate: TranslatePipe) {
    //this.dataSource.data = TREE_DATA;
  }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories() {
    this.spinner.show();
    this.productService.getCategories()
      .subscribe({
        next: (resp: any) => {
          if (CommonUtil.isNullOrEmptyOrUndefined(resp.dataList)) {
            this.dataSource.data = new Array();
          }
          else {
            this.dataSource.data = resp.dataList;
          }
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.spinner.hide();
        }
      });
  }

  public hasError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.hasFormFieldError(fieldGroup, fieldName);
  }

  public getError = (fieldGroup: any, fieldName: string) => {
    return CommonUtil.getFieldError(fieldGroup, fieldName, this.translate);
  }

  canShowCreate() {
    return this.hasError(this.categoryFormGroup, 'categoryName');
  }

  toggleCategorysModal() {
    this.showAddCategoryModal = !this.showAddCategoryModal;
  }

  //done to avoid reopening issue on click outside modal area.
  handlecategoryModal(event: boolean) {
    this.showAddCategoryModal = event;
  }

  addCategory() {
    this.spinner.show();
    let body = {
      name: this.categoryFormGroup.value.categoryName,
      parentId: this.selectedParentId
    }
    this.productService.createCategory(body)
      .subscribe({
        next: (resp: any) => {
          this.loadCategories();
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.spinner.hide();
          this.toggleCategorysModal();
        }
      });
  }

  addItem(parentId: any) {
    this.selectedParentId = parentId;
    this.toggleCategorysModal();
  }

  deleteItem(itemId: any) {
    this.spinner.show()
    this.productService.deleteCategory(itemId)
      .subscribe({
        next: (resp: any) => {
          this.loadCategories();
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.spinner.hide();
        }
      });
  }

}
