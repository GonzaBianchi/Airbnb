import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LodgingviewComponent } from './lodgingview.component';

describe('LodgingviewComponent', () => {
  let component: LodgingviewComponent;
  let fixture: ComponentFixture<LodgingviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LodgingviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LodgingviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
