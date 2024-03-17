import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TenantTasksComponent } from './tenant-tasks.component';

describe('TenantTasksComponent', () => {
  let component: TenantTasksComponent;
  let fixture: ComponentFixture<TenantTasksComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TenantTasksComponent]
    });
    fixture = TestBed.createComponent(TenantTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
