import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnboardSupplierComponent } from './onboard-supplier.component';

describe('OnboardSupplierComponent', () => {
  let component: OnboardSupplierComponent;
  let fixture: ComponentFixture<OnboardSupplierComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OnboardSupplierComponent]
    });
    fixture = TestBed.createComponent(OnboardSupplierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
