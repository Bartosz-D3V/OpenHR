import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MdButtonModule, MdMenuModule } from '@angular/material';

import { AvatarComponent } from './avatar.component';
import { InitialsPipe } from '../../pipes/initials/initials.pipe';
import { Subject } from '../../domain/subject/subject';
import { Address } from '../../domain/subject/address';

describe('AvatarComponent', () => {
  let component: AvatarComponent;
  let fixture: ComponentFixture<AvatarComponent>;
  const mockSubject = new Subject('John', 'Test', new Date(1, 2, 1950),
    'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        InitialsPipe,
        AvatarComponent
      ],
      imports: [
        MdMenuModule,
        MdButtonModule
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvatarComponent);
    component = fixture.componentInstance;
    component.subject = mockSubject;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should display initials of the subject inside the button', () => {
    const actualInitials: string = fixture.nativeElement.querySelector('#shared-avatar').innerText;

    expect(actualInitials).toEqual('JT');
  });
});
