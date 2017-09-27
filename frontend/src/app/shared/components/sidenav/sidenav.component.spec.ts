import { async, ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';

import { MdSidenav, MdSidenavModule } from '@angular/material';

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

  describe('ngOnInit method', () => {
    let router: Router;
    let sidenav: MdSidenav;

    beforeEach(inject([Router], (_router: Router) => {
      router = _router;
      sidenav = fixture.debugElement.query(By.directive(MdSidenav)).componentInstance;
    }));

    it('should call isScreenSmall every time when router emits event', () => {
      spyOn(component, 'isScreenSmall');
      fixture.detectChanges();

      expect(component.isScreenSmall).toHaveBeenCalled();
    });

    // FIX ME
    xit('should close sidenav if screen is small', fakeAsync(() => {
      spyOn(component, 'isScreenSmall').and.returnValue(true);
      spyOn(sidenav, 'close');
      tick();
      fixture.detectChanges();

      expect(component.isScreenSmall).toHaveBeenCalled();
      expect(sidenav.close).toHaveBeenCalled();
    }));

  });

  // FIX ME
  xdescribe('isScreenSmall method', () => {

    it('should return true if max-width is less or equal than 840px', () => {
      spyOn(window, 'matchMedia').and.returnValue({
          matches: true
        }
      );
      fixture.detectChanges();
      expect(component.isScreenSmall()).toBeTruthy();
      expect(component instanceof SidenavComponent).toBeTruthy();
      expect(component.sidenav.opened).toBeFalsy();
    });

  });

});
