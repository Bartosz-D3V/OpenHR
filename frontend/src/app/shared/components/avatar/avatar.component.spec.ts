import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatButtonModule, MatMenuModule } from '@angular/material';

import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { AvatarComponent } from './avatar.component';
import { InitialsPipe } from '../../pipes/initials/initials.pipe';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';

describe('AvatarComponent', () => {
  let component: AvatarComponent;
  let fixture: ComponentFixture<AvatarComponent>;
  let jwtHelper: JwtHelperService;
  const mockUser: LightweightSubject = new LightweightSubject(2199, 'John', 'Test', 'Tester');

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InitialsPipe, AvatarComponent],
      imports: [MatMenuModule, MatButtonModule, RouterTestingModule],
      providers: [JwtHelperService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvatarComponent);
    component = fixture.componentInstance;
    component.user = mockUser;
    fixture.detectChanges();
    jwtHelper = TestBed.get(JwtHelperService);

    spyOn(component['_router'], 'navigate');
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should display initials of the subject inside the button', () => {
    const actualInitials: string = fixture.nativeElement.querySelector('#shared-avatar').innerText;

    expect(actualInitials).toEqual('JT');
  });

  it('logout method should call removeToken method from JWT Helper Service', () => {
    spyOn(component['_jwtHelper'], 'removeToken');
    component.logout();

    expect(component['_jwtHelper'].removeToken).toHaveBeenCalled();
  });
});
