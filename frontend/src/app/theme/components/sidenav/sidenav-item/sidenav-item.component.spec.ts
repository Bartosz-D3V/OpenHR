import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavItemComponent } from './sidenav-item.component';
import { MdIconModule } from '@angular/material';
import { Item } from './item';

describe('SidenavItemComponent', () => {
  let component: SidenavItemComponent;
  let fixture: ComponentFixture<SidenavItemComponent>;
  let item: Item;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        SidenavItemComponent
      ],
      imports: [
        MdIconModule,
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    item = new Item('Test button', 'star', true);
    component.item = this.item;
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
