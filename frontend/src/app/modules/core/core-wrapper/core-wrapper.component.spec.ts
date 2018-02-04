import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { MatDialogModule, MatIconModule, MatMenuModule, MatSidenavModule, MatToolbarModule } from '@angular/material';

import { SidenavItemListComponent } from '../../../shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';
import { SidenavComponent } from '../../../shared/components/sidenav/sidenav.component';
import { AvatarComponent } from '../../../shared/components/avatar/avatar.component';
import { InitialsPipe } from '../../../shared/pipes/initials/initials.pipe';
import { AppComponent } from '../../../boot/app.component';
import { LightweightSubjectService } from './service/lightweight-subject.service';
import { CoreWrapperComponent } from './core-wrapper.component';

describe('CoreComponent', () => {
  let component: CoreWrapperComponent;
  let fixture: ComponentFixture<CoreWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CoreWrapperComponent,
        AppComponent,
        AvatarComponent,
        SidenavComponent,
        SidenavItemListComponent,
        InitialsPipe,
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        NoopAnimationsModule,
        MatSidenavModule,
        MatToolbarModule,
        MatMenuModule,
        MatIconModule,
        MatDialogModule,
      ],
      providers: [
        LightweightSubjectService,
        ErrorResolverService,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoreWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
