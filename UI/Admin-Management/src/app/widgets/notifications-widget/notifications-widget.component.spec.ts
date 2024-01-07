import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationsWidgetComponent } from './notifications-widget.component';

describe('NotificationsWidgetComponent', () => {
  let component: NotificationsWidgetComponent;
  let fixture: ComponentFixture<NotificationsWidgetComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotificationsWidgetComponent]
    });
    fixture = TestBed.createComponent(NotificationsWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
