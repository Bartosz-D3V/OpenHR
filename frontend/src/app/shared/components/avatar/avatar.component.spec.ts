import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { MatButtonModule, MatMenuModule } from '@angular/material';

import { AvatarComponent } from './avatar.component';
import { User } from '../../domain/user/user';
import { InitialsPipe } from '../../pipes/initials/initials.pipe';

describe('AvatarComponent', () => {
  let component: AvatarComponent;
  let fixture: ComponentFixture<AvatarComponent>;
  const mockUser = new User(2199, 'john.test', 'John Test', null);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        InitialsPipe,
        AvatarComponent,
      ],
      imports: [
        MatMenuModule,
        MatButtonModule,
        RouterTestingModule,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvatarComponent);
    component = fixture.componentInstance;
    component.user = mockUser;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should display initials of the subject inside the button', () => {
    const actualInitials: string = fixture.nativeElement.querySelector('#shared-avatar').innerText;

    expect(actualInitials).toEqual('JT');
  });

  it('logout method should remove token from localStorage', () => {
    window.localStorage.setItem('openHRAuth', 't0k3n');
    component.logout();

    expect(window.localStorage.getItem('openHRAuth')).toBeNull();
  });
});
