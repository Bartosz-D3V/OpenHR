import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavItemListComponent } from './sidenav-item-list.component';
import { SidenavItemComponent } from '../sidenav-item/sidenav-item.component';

describe('SidenavItemListComponent', () => {
  let component: SidenavItemListComponent;
  let fixture: ComponentFixture<SidenavItemListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        SidenavItemListComponent,
        SidenavItemComponent,
      ],
      imports: []
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavItemListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
