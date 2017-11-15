import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { MatButtonModule, MatMenuModule, MatSidenavModule, MatToolbarModule } from '@angular/material';

import { SidenavComponent } from './sidenav.component';
import { SidenavItemComponent } from './sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './sidenav-item-list/sidenav-item-list.component';
import { AvatarComponent } from '../avatar/avatar.component';
import { InitialsPipe } from '../../pipes/initials/initials.pipe';
import { User } from '../../domain/user/user';

describe('SidenavComponent', () => {
  const mockUser: User = new User(2199, 'john.test', 'John Test');
  let component: SidenavComponent;
  let fixture: ComponentFixture<SidenavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        InitialsPipe,
        AvatarComponent,
        SidenavComponent,
        SidenavItemListComponent,
        SidenavItemComponent,
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        MatSidenavModule,
        MatMenuModule,
        MatToolbarModule,
        MatButtonModule,
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavComponent);
    component = fixture.componentInstance;
    component.user = mockUser;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  xdescribe('isScreenSmall method', () => {

    it('should return true if max-width is less or equal than 840px', () => {
      spyOn(window, 'matchMedia').and.returnValue({
        matches: true,
      });
      fixture.detectChanges();
      expect(component.isScreenSmall()).toBeTruthy();
    });

  });

});
