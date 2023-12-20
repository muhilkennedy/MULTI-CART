import { TestBed } from '@angular/core/testing';

import { UserNotficationService } from './user-notfication.service';

describe('UserNotficationService', () => {
  let service: UserNotficationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserNotficationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
