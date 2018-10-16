import { RouterTestingModule } from '@angular/router/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { SidenavItemListComponent } from './sidenav-item-list.component';
import { Role } from '@shared/domain/subject/role';

describe('SidenavItemListComponent', () => {
  let component: SidenavItemListComponent;
  let fixture: ComponentFixture<SidenavItemListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidenavItemListComponent],
      imports: [RouterTestingModule],
      providers: [JwtHelperService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavItemListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(component['_jwtHelper'], 'getUsersRole').and.returnValue([Role.MANAGER]);
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
