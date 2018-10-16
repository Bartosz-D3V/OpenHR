import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { MatButtonModule, MatDialogModule } from '@angular/material';

import { PromptModalComponent } from './prompt-modal.component';

describe('PromptModalComponent', () => {
  let component: PromptModalComponent;
  let fixture: ComponentFixture<PromptModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PromptModalComponent],
      imports: [MatButtonModule, NoopAnimationsModule, FormsModule, MatDialogModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PromptModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
