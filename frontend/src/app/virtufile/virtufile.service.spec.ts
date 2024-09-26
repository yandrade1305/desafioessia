import { TestBed } from '@angular/core/testing';

import { VirtufileService } from './virtufile.service';

describe('VirtufileService', () => {
  let service: VirtufileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VirtufileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
