import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { MdButtonModule, MdMenuModule, MdSidenavModule, MdToolbarModule } from '@angular/material';

import { SidenavComponent } from './sidenav.component';
import { SidenavItemComponent } from './sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './sidenav-item-list/sidenav-item-list.component';
import { AvatarComponent } from '../avatar/avatar.component';
import { Subject } from '../../domain/subject/subject';
import { Address } from '../../domain/subject/address';
import { InitialsPipe } from '../../pipes/initials/initials.pipe';

describe('SidenavComponent', () => {
  const mockSubject: Subject = new Subject('John', 'Test', new Date(1, 2, 1950),
    'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));
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
        MdSidenavModule,
        MdMenuModule,
        MdToolbarModule,
        MdButtonModule
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavComponent);
    component = fixture.componentInstance;
    component.subject = mockSubject;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  xdescribe('isScreenSmall method', () => {

    it('should return true if max-width is less or equal than 840px', () => {
      spyOn(window, 'matchMedia').and.returnValue({
        matches: true
      });
      fixture.detectChanges();
      expect(component.isScreenSmall()).toBeTruthy();
    });

  });

});
