import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllowanceSettingsComponent } from './allowance-settings.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AllowanceSettingsComponent', () => {
  let component: AllowanceSettingsComponent;
  let fixture: ComponentFixture<AllowanceSettingsComponent>;

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [AllowanceSettingsComponent],
        imports: [ReactiveFormsModule, FormsModule, HttpClientTestingModule, NoopAnimationsModule],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(AllowanceSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
