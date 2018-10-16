import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material';
import { OverlayContainer } from '@angular/cdk/overlay';

import { StaticModalComponent } from './static-modal.component';

describe('StaticModalComponent', () => {
  let component: StaticModalComponent;
  let fixture: ComponentFixture<StaticModalComponent>;
  const overlayContainerElement: HTMLElement = document.createElement('div');

  class FakeMatDialog {
    public open(): void {}
  }
  class FakeMatDialogRef {
    public close(): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [StaticModalComponent],
      imports: [NoopAnimationsModule, MatDialogModule],
      providers: [
        {
          provide: MatDialogRef,
          useClass: FakeMatDialogRef,
        },
        {
          provide: MatDialog,
          useClass: FakeMatDialog,
        },
        {
          provide: OverlayContainer,
        },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaticModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('close method should close modal', () => {
    component.close();

    expect(overlayContainerElement.querySelectorAll('mat-dialog-container').length).toBe(0, 'Expected no open dialogs.');
  });
});
