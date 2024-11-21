import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LodgingBookingsComponent } from './lodging-bookings.component';

describe('LodgingBookingsComponent', () => {
  let component: LodgingBookingsComponent;
  let fixture: ComponentFixture<LodgingBookingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LodgingBookingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LodgingBookingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
