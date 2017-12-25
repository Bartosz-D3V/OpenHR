import { RouterTestingModule } from '@angular/router/testing';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { TestBed, async } from '@angular/core/testing';

import { MatButtonModule, MatIconModule, MatMenuModule, MatSidenavModule, MatToolbarModule } from '@angular/material';

import { AppComponent } from './app.component';
import { SidenavComponent } from '../shared/components/sidenav/sidenav.component';
import { SidenavItemListComponent } from '../shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { AvatarComponent } from '../shared/components/avatar/avatar.component';
import { InitialsPipe } from '../shared/pipes/initials/initials.pipe';
import { User } from '../shared/domain/user/user';

describe('AppComponent', () => {
  const mockUser: User = new User(2199, 'john.test', 'John Test', null);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        AvatarComponent,
        SidenavComponent,
        SidenavItemListComponent,
        InitialsPipe,
      ],
      imports: [
        BrowserAnimationsModule,
        MatSidenavModule,
        MatToolbarModule,
        MatIconModule,
        MatMenuModule,
        MatButtonModule,
        RouterTestingModule,
      ],
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    app.subject = mockUser;
    expect(app).toBeTruthy();
  }));
});
