import { Subject } from '../../../../../shared/domain/subject/subject';
import { Manager } from './manager';

export class Employee {
  public employeeId: number;
  public subject: Subject;
  public manager: Manager;

  constructor() {
  }
}
