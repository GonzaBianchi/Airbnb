import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospedajeComponent } from './hospedaje.component';

describe('HospedajeComponent', () => {
  let component: HospedajeComponent;
  let fixture: ComponentFixture<HospedajeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospedajeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospedajeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
