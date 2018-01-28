import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule } from '@angular/material';

import { LoginBoxComponent } from './login-box.component';

describe('LoginBoxComponent', () => {
  let component: LoginBoxComponent;
  let fixture: ComponentFixture<LoginBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        MatCardModule,
        MatButtonModule,
        MatIconModule,
        MatFormFieldModule,
      ],
      declarations: [
        LoginBoxComponent,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
