import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { MatMenuModule, MatSidenavModule, MatToolbarModule } from '@angular/material';

import { CoreComponent } from './core.component';
import { AppComponent } from '../../boot/app.component';
import { SidenavComponent } from '../../shared/components/sidenav/sidenav.component';
import { SidenavItemComponent } from '../../shared/components/sidenav/sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from '../../shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { AvatarComponent } from '../../shared/components/avatar/avatar.component';
import { InitialsPipe } from '../../shared/pipes/initials/initials.pipe';

describe('CoreComponent', () => {
  let component: CoreComponent;
  let fixture: ComponentFixture<CoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CoreComponent,
        AppComponent,
        AvatarComponent,
        SidenavComponent,
        SidenavItemComponent,
        SidenavItemListComponent,
        InitialsPipe,
      ],
      imports: [
        RouterTestingModule,
        NoopAnimationsModule,
        MatSidenavModule,
        MatToolbarModule,
        MatMenuModule,
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
