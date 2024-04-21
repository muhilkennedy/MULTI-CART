import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProductSpecificationsComponent } from './edit-product-specifications.component';

describe('EditProductSpecificationsComponent', () => {
  let component: EditProductSpecificationsComponent;
  let fixture: ComponentFixture<EditProductSpecificationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditProductSpecificationsComponent]
    });
    fixture = TestBed.createComponent(EditProductSpecificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
