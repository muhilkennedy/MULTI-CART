import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingtasksWidgetComponent } from './pendingtasks-widget.component';

describe('PendingtasksWidgetComponent', () => {
  let component: PendingtasksWidgetComponent;
  let fixture: ComponentFixture<PendingtasksWidgetComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PendingtasksWidgetComponent]
    });
    fixture = TestBed.createComponent(PendingtasksWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
