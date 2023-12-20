import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TenantInfoComponent } from './tenant-info.component';

describe('TenantInfoComponent', () => {
  let component: TenantInfoComponent;
  let fixture: ComponentFixture<TenantInfoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TenantInfoComponent]
    });
    fixture = TestBed.createComponent(TenantInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
