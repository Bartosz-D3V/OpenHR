import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MdDialogRef } from '@angular/material';

import { StaticModalComponent } from './static-modal.component';

describe('StaticModalComponent', () => {
  let component: StaticModalComponent;
  let fixture: ComponentFixture<StaticModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        StaticModalComponent
      ],
      imports: [
        NoopAnimationsModule
      ],
      providers: [
        {
          provide: MdDialogRef
        },
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaticModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

});
