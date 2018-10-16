import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { MatButtonModule, MatIconModule, MatMenuModule, MatSidenavModule, MatToolbarModule } from '@angular/material';

import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { SidenavItemListComponent } from './sidenav-item-list/sidenav-item-list.component';
import { SidenavComponent } from './sidenav.component';
import { AvatarComponent } from '../avatar/avatar.component';
import { InitialsPipe } from '../../pipes/initials/initials.pipe';
import { ResponsiveHelperService } from '../../services/responsive-helper/responsive-helper.service';

describe('SidenavComponent', () => {
  const mockUser: LightweightSubject = new LightweightSubject(2199, 'John', 'Test', 'Tester');
  let component: SidenavComponent;
  let fixture: ComponentFixture<SidenavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InitialsPipe, AvatarComponent, SidenavComponent, SidenavItemListComponent],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        MatIconModule,
        MatSidenavModule,
        MatMenuModule,
        MatToolbarModule,
        MatButtonModule,
      ],
      providers: [ResponsiveHelperService],
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

  describe('isScreenSmall method', () => {
    it('should return true if max-width is less or equal than 840px', () => {
      spyOn(component['_responsiveHelper'], 'isSmallTablet').and.returnValue(true);

      expect(component.isScreenSmall()).toBeTruthy();
    });

    it('should return false if max-width is greater than 840px', () => {
      spyOn(component['_responsiveHelper'], 'isSmallTablet').and.returnValue(false);

      expect(component.isScreenSmall()).toBeFalsy();
    });
  });
});
