import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { Subject } from '../../classes/subject/subject';
import { Address } from '../../classes/subject/address';

@Injectable()
export class SubjectService {

  constructor(private http: Http) {
  }

  public getCurrentSubject() {
    return new Subject('John', null, 'Test', new Date(1, 2, 1950), 'Mentor', '12345678', 'test@test.com',
      new Address('', '', '', '', '', ''));
  }

}
