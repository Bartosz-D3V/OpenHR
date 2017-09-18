import { TestBed, async } from '@angular/core/testing';

import { AppComponent } from './app.component';
import { SidenavItemListComponent } from './shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { SidenavItemComponent } from './shared/components/sidenav/sidenav-item/sidenav-item.component';
import { SidenavComponent } from './shared/components/sidenav/sidenav.component';
import { MdIconModule, MdSidenavModule, MdToolbarModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        SidenavComponent,
        SidenavItemListComponent,
        SidenavItemComponent,
      ],
      imports: [
        BrowserAnimationsModule,
        MdSidenavModule,
        MdToolbarModule,
        MdIconModule
      ],
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
