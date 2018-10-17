import { Injectable } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { OverlayContainer } from '@angular/cdk/overlay';
import { MAT_DIALOG_DATA, MatButtonModule, MatDialogModule, MatDialogRef } from '@angular/material';

import { TokenExpirationModalComponent } from './token-expiration-modal.component';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { TokenExpirationService } from '@shared/components/token-expiration-modal/service/token-expiration.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';

describe('TokenExpirationModalComponent', () => {
  let component: TokenExpirationModalComponent;
  let fixture: ComponentFixture<TokenExpirationModalComponent>;
  let overlayContainerElement: HTMLElement;

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TokenExpirationModalComponent],
      imports: [RouterTestingModule, HttpClientTestingModule, FormsModule, MatDialogModule, MatButtonModule],
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
          provide: MAT_DIALOG_DATA,
          useValue: { cancelled: false },
        },
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
    fixture = TestBed.createComponent(TokenExpirationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(component['_router'], 'navigate');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
