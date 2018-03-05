import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { Subject } from '../../domain/subject/subject';

@Injectable()
export class CurrentUserProviderService {
  private currentSubject: Observable<Subject>;

  public setSubject(subject: Subject): void {
    Observable.create((observer) => {
      observer.next(subject);
    });
  }

  public getSubject(): Observable<Subject> {
    return this.currentSubject;
  }
}
