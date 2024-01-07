import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarWidgetComponent } from './calendar-widget.component';

describe('CalendarWidgetComponent', () => {
  let component: CalendarWidgetComponent;
  let fixture: ComponentFixture<CalendarWidgetComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CalendarWidgetComponent]
    });
    fixture = TestBed.createComponent(CalendarWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
