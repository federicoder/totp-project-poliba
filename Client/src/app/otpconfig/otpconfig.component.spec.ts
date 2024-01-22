import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtpconfigComponent } from './otpconfig.component';

describe('OtpconfigComponent', () => {
  let component: OtpconfigComponent;
  let fixture: ComponentFixture<OtpconfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OtpconfigComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OtpconfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
