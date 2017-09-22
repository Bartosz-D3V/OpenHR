import { TestBed, inject } from '@angular/core/testing';

import { SubjectService } from './subject.service';
import { HttpModule } from '@angular/http';

describe('SubjectService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
      ],
      providers: [SubjectService]
    });
  });

  it('should be created', inject([SubjectService], (service: SubjectService) => {
    expect(service).toBeTruthy();
  }));
});
