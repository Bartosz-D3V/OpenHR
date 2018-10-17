import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NumberIndicatorComponent } from './number-indicator.component';

describe('NumberIndicatorComponent', () => {
  let component: NumberIndicatorComponent;
  let fixture: ComponentFixture<NumberIndicatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NumberIndicatorComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NumberIndicatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
