import { TestBed } from '@angular/core/testing';

import { SampleKudoService } from './sample-kudo.service';

describe('SampleKudoService', () => {
  let service: SampleKudoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SampleKudoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
