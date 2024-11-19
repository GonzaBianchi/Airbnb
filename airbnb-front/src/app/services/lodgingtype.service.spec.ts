import { TestBed } from '@angular/core/testing';

import { LodgingtypeService } from './lodgingtype.service';

describe('LodgingtypeService', () => {
  let service: LodgingtypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LodgingtypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
