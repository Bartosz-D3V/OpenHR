import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MdDialogRef } from '@angular/material';
import { OverlayContainer } from '@angular/cdk/overlay';

import { StaticModalComponent } from './static-modal.component';

describe('StaticModalComponent', () => {
  let component: StaticModalComponent;
  let fixture: ComponentFixture<StaticModalComponent>;
  let overlayContainerElement: HTMLElement;

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
          provide: MdDialogRef,
        },
        {
          provide: OverlayContainer, useFactory: () => {
          overlayContainerElement = document.createElement('div');
          return {getContainerElement: () => overlayContainerElement};
        }
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

  xit('close method should close modal', () => {
    component.close();

    expect(overlayContainerElement.querySelectorAll('mat-dialog-container').length)
      .toBe(0, 'Expected no open dialogs.');
  });

});
