import { TestBed } from '@angular/core/testing';

import { SampleUserService } from './sample-user.service';

describe('SampleUserService', () => {
  let service: SampleUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SampleUserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
