import { Subject } from '@shared/domain/subject/subject';

export class ObjectHelper {
  public static removeSubjectHelperProperties(subject: Subject): Subject {
    delete subject.user['repeatPassword'];
    delete subject.role;
    return subject;
  }
}
