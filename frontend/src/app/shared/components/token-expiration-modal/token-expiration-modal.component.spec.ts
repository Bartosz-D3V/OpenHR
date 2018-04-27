import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { OverlayContainer } from '@angular/cdk/overlay';
import { MatDialogRef } from '@angular/material';

import { TokenExpirationModalComponent } from './token-expiration-modal.component';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { TokenExpirationService } from '@shared/components/token-expiration-modal/service/token-expiration.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Injectable } from '@angular/core';

describe('TokenExpirationModalComponent', () => {
  let component: TokenExpirationModalComponent;
  let fixture: ComponentFixture<TokenExpirationModalComponent>;
  let overlayContainerElement: HTMLElement;

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [TokenExpirationModalComponent],
        providers: [
          TokenExpirationService,
          JwtHelperService,
          {
            provide: ErrorResolverService,
            useClass: FakeErrorResolverService,
          },
          {
            provide: MatDialogRef,
          },
          {
            provide: OverlayContainer,
            useFactory: () => {
              overlayContainerElement = document.createElement('div');
              return { getContainerElement: () => overlayContainerElement };
            },
          },
        ],
        imports: [RouterTestingModule, HttpClientTestingModule],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(TokenExpirationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(component['_router'], 'navigate');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
