import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { MdSidenavModule } from '@angular/material';

import { SidenavComponent } from './sidenav.component';
import { SidenavItemComponent } from './sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './sidenav-item-list/sidenav-item-list.component';

describe('SidenavComponent', () => {
  let component: SidenavComponent;
  let fixture: ComponentFixture<SidenavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        SidenavComponent,
        SidenavItemListComponent,
        SidenavItemComponent,
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        MdSidenavModule,
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavComponent);
    component = fixture.componentInstance;
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
