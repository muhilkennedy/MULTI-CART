import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProductImagesComponent } from './edit-product-images.component';

describe('EditProductImagesComponent', () => {
  let component: EditProductImagesComponent;
  let fixture: ComponentFixture<EditProductImagesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditProductImagesComponent]
    });
    fixture = TestBed.createComponent(EditProductImagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
