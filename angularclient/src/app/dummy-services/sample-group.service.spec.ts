import { TestBed } from '@angular/core/testing';

import { SampleGroupService } from './sample-group.service';

describe('SampleServiceService', () => {
  let service: SampleGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SampleGroupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
