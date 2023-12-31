import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TenantSecurityComponent } from './tenant-security.component';

describe('TenantSecurityComponent', () => {
  let component: TenantSecurityComponent;
  let fixture: ComponentFixture<TenantSecurityComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TenantSecurityComponent]
    });
    fixture = TestBed.createComponent(TenantSecurityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
