import { TestBed } from '@angular/core/testing';

import { CountrycityService } from './countrycity.service';

describe('CountrycityService', () => {
  let service: CountrycityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CountrycityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
