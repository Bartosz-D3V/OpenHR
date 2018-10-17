import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Observable } from 'rxjs/Observable';
import { MatDialogModule, MatIconModule, MatMenuModule, MatSidenavModule, MatToolbarModule } from '@angular/material';

import { SidenavItemListComponent } from '@shared/components/sidenav/sidenav-item-list/sidenav-item-list.component';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { SidenavComponent } from '@shared/components/sidenav/sidenav.component';
import { AvatarComponent } from '@shared/components/avatar/avatar.component';
import { InitialsPipe } from '@shared/pipes/initials/initials.pipe';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { AppComponent } from '@boot/app.component';
import { LightweightSubjectService } from './service/lightweight-subject.service';
import { CoreWrapperComponent } from './core-wrapper.component';
import { TokenObserverService } from '@shared/services/token-observer/token-observer.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { OverlayContainer } from '@angular/cdk/overlay';

describe('CoreComponent', () => {
  let component: CoreWrapperComponent;
  let fixture: ComponentFixture<CoreWrapperComponent>;
  const mockUser: LightweightSubject = new LightweightSubject(1, 'John', 'Test', 'Tester');
  let overlayContainerElement: HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CoreWrapperComponent, AppComponent, AvatarComponent, SidenavComponent, SidenavItemListComponent, InitialsPipe],
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
        TokenObserverService,
        JwtHelperService,
        {
          provide: OverlayContainer,
          useFactory: () => {
            overlayContainerElement = document.createElement('div');
            return { getContainerElement: () => overlayContainerElement };
          },
        },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoreWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(component['_router'], 'navigate');
    spyOn(component['_tokenObserver'], 'observe');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    beforeEach(() => {
      spyOn(component['_lightweightSubject'], 'getUser').and.returnValue(Observable.of(mockUser));
      spyOn(component['_jwtHelper'], 'getSubjectId');
    });

    it('should fetch and assign all values to the User object', () => {
      component.ngOnInit();
      const actualUser: LightweightSubject = component.user;

      expect(actualUser).toBeDefined();
      expect(actualUser.subjectId).toEqual(mockUser.subjectId);
      expect(actualUser.firstName).toBe(mockUser.firstName);
      expect(actualUser.lastName).toBe(mockUser.lastName);
      expect(actualUser.fullName).toBe(mockUser.firstName + ' ' + mockUser.lastName);
    });

    it('should navigate to dashboard page', () => {
      component.ngOnInit();

      expect(component['_router'].navigate).toHaveBeenCalled();
    });
  });
});
