import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatButtonModule, MatGridListModule, MatIconModule, MatMenuModule } from '@angular/material';

import { StyleManagerService } from '../../services/style-manager/style-manager.service';
import { ThemeStorageService } from './service/theme-storage.service';
import { ThemePickerComponent } from './theme-picker.component';

describe('ThemePickerComponent', () => {
  let component: ThemePickerComponent;
  let fixture: ComponentFixture<ThemePickerComponent>;

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [ThemePickerComponent],
        providers: [ThemeStorageService, StyleManagerService],
        imports: [MatButtonModule, MatIconModule, MatMenuModule, MatGridListModule],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(ThemePickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
