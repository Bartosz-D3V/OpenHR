import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { OverlayContainer } from '@angular/cdk/overlay';
import { MatDialogRef } from '@angular/material';

import { TokenExpirationModalComponent } from './token-expiration-modal.component';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('TokenExpirationModalComponent', () => {
  let component: TokenExpirationModalComponent;
  let fixture: ComponentFixture<TokenExpirationModalComponent>;
  let overlayContainerElement: HTMLElement;

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [TokenExpirationModalComponent],
        providers: [
          JwtHelperService,
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
        imports: [RouterTestingModule],
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
