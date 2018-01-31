import { RouterTestingModule } from '@angular/router/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JwtHelperService } from '../../../services/jwt/jwt-helper.service';
import { SidenavItemListComponent } from './sidenav-item-list.component';

describe('SidenavItemListComponent', () => {
  let component: SidenavItemListComponent;
  let fixture: ComponentFixture<SidenavItemListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        SidenavItemListComponent,
      ],
      imports: [
        RouterTestingModule,
      ],
      providers: [
        JwtHelperService,
      ],
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

  it('hasRole should call the JWT Helper Service', () => {
    spyOn(component['_jwtHelper'], 'hasRole');
    component.hasRole('Admin');

    expect(component['_jwtHelper'].hasRole).toHaveBeenCalled();
  });
});
