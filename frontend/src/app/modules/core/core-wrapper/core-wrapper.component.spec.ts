import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import {MatIconModule, MatMenuModule, MatSidenavModule, MatToolbarModule} from '@angular/material';

import { CoreWrapperComponent } from './core-wrapper.component';
import { AppComponent } from '../../../boot/app.component';
import { SidenavComponent } from '../../../shared/components/sidenav/sidenav.component';
import { SidenavItemListComponent } from '../../../shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { AvatarComponent } from '../../../shared/components/avatar/avatar.component';
import { InitialsPipe } from '../../../shared/pipes/initials/initials.pipe';
import { ThemePickerComponent } from '../../../shared/components/theme-picker/theme-picker.component';

describe('CoreComponent', () => {
  let component: CoreWrapperComponent;
  let fixture: ComponentFixture<CoreWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CoreWrapperComponent,
        AppComponent,
        AvatarComponent,
        SidenavComponent,
        SidenavItemListComponent,
        InitialsPipe,
        ThemePickerComponent,
      ],
      imports: [
        RouterTestingModule,
        NoopAnimationsModule,
        MatSidenavModule,
        MatToolbarModule,
        MatMenuModule,
        MatIconModule,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoreWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
