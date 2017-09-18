import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavComponent } from './sidenav.component';
import { SidenavItemComponent } from './sidenav-item/sidenav-item.component';
import { SidenavItemListComponent } from './sidenav-item-list/sidenav-item-list.component';
import { MdSidenavModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
        BrowserAnimationsModule,
        MdSidenavModule,
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
