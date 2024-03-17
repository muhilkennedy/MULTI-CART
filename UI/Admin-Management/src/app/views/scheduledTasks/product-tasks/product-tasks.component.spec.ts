import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductTasksComponent } from './product-tasks.component';

describe('ProductTasksComponent', () => {
  let component: ProductTasksComponent;
  let fixture: ComponentFixture<ProductTasksComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductTasksComponent]
    });
    fixture = TestBed.createComponent(ProductTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
