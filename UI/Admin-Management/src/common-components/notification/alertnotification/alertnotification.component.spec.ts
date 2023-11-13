import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlertnotificationComponent } from './alertnotification.component';

describe('AlertnotificationComponent', () => {
  let component: AlertnotificationComponent;
  let fixture: ComponentFixture<AlertnotificationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlertnotificationComponent]
    });
    fixture = TestBed.createComponent(AlertnotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
