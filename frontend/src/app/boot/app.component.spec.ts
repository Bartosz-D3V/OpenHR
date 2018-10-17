import { RouterTestingModule } from '@angular/router/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TestBed, async } from '@angular/core/testing';
import { MatButtonModule, MatIconModule, MatMenuModule, MatSidenavModule, MatToolbarModule } from '@angular/material';

import { AppComponent } from './app.component';
import { SidenavComponent } from '@shared/components/sidenav/sidenav.component';
import { SidenavItemListComponent } from '@shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { AvatarComponent } from '@shared/components/avatar/avatar.component';
import { InitialsPipe } from '@shared/pipes/initials/initials.pipe';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppComponent, AvatarComponent, SidenavComponent, SidenavItemListComponent, InitialsPipe],
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
    expect(app).toBeTruthy();
  }));
});
