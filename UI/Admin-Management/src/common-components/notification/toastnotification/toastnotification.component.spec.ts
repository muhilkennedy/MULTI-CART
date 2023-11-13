import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToastnotificationComponent } from './toastnotification.component';

describe('ToastnotificationComponent', () => {
  let component: ToastnotificationComponent;
  let fixture: ComponentFixture<ToastnotificationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ToastnotificationComponent]
    });
    fixture = TestBed.createComponent(ToastnotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
