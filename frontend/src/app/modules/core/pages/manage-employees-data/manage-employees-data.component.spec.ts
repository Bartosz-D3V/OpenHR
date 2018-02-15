import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageEmployeesDataComponent } from './manage-employees-data.component';

describe('ManageEmployeesDataComponent', () => {
  let component: ManageEmployeesDataComponent;
  let fixture: ComponentFixture<ManageEmployeesDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageEmployeesDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageEmployeesDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
