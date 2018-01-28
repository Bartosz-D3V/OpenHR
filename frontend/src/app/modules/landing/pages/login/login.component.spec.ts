import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule } from '@angular/material';

import { LoginBoxComponent } from '../../../../shared/components/login-box/login-box.component';
import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        MatCardModule,
        MatButtonModule,
        MatIconModule,
        MatFormFieldModule,
      ],
      declarations: [
        LoginComponent,
        LoginBoxComponent,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
