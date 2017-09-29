import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavItemComponent } from './sidenav-item.component';
import { MdIconModule } from '@angular/material';
import { SidenavItem } from './sidenav-item';
import { RouterTestingModule } from '@angular/router/testing';

describe('SidenavItemComponent', () => {
  let component: SidenavItemComponent;
  let fixture: ComponentFixture<SidenavItemComponent>;
  let sidenavItem: SidenavItem;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        SidenavItemComponent
      ],
      imports: [
        MdIconModule,
        RouterTestingModule,
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    sidenavItem = new SidenavItem('Test button', 'star', '/', true);

    fixture = TestBed.createComponent(SidenavItemComponent);
    component = fixture.componentInstance;
    component.sidenavItem = sidenavItem;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

});
