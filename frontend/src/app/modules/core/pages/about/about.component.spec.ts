import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatCardModule, MatToolbarModule } from '@angular/material';

import { AboutComponent } from './about.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { ThemePickerComponent } from '../../../../shared/components/theme-picker/theme-picker.component';

describe('AboutComponent', () => {
  let component: AboutComponent;
  let fixture: ComponentFixture<AboutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AboutComponent,
        PageHeaderComponent,
        CapitalizePipe,
        ThemePickerComponent,
      ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatCardModule,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
