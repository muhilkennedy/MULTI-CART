import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EdittenantComponent } from './edittenant.component';

describe('EdittenantComponent', () => {
  let component: EdittenantComponent;
  let fixture: ComponentFixture<EdittenantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EdittenantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EdittenantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
