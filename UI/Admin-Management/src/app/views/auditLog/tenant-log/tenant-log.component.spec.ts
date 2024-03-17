import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TenantLogComponent } from './tenant-log.component';

describe('TenantLogComponent', () => {
  let component: TenantLogComponent;
  let fixture: ComponentFixture<TenantLogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TenantLogComponent]
    });
    fixture = TestBed.createComponent(TenantLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
