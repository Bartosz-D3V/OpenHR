import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatButtonModule, MatDialogModule, MatDialogRef, MatFormFieldModule, MatInputModule } from '@angular/material';
import { OverlayContainer } from '@angular/cdk/overlay';

import { InputModalComponent } from './input-modal.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('InputModalComponent', () => {
  let component: InputModalComponent;
  let fixture: ComponentFixture<InputModalComponent>;
  let overlayContainerElement: HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InputModalComponent],
      imports: [NoopAnimationsModule, FormsModule, MatInputModule, MatDialogModule, MatInputModule, MatButtonModule, MatFormFieldModule],
      providers: [
        {
          provide: MatDialogRef,
        },
        { provide: MAT_DIALOG_DATA, useValue: { refusalReason: null, cancelled: false } },
        {
          provide: OverlayContainer,
          useFactory: () => {
            overlayContainerElement = document.createElement('div');
            return { getContainerElement: () => overlayContainerElement };
          },
        },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InputModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('cancel method should set cancelled to true', () => {
    expect(component.data.cancelled).toBeFalsy();
    component.cancel();

    expect(component.data.cancelled).toBeTruthy();
  });
});
