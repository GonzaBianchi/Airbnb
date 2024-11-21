import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LodgingsComponent } from './lodgings.component';

describe('LodgingsComponent', () => {
  let component: LodgingsComponent;
  let fixture: ComponentFixture<LodgingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LodgingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LodgingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
