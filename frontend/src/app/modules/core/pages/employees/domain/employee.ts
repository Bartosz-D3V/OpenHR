import { Subject } from '../../personal-details/domain/subject';
import { Manager } from './manager';

export class Employee {
  public employeeId: number;
  private _subject?: Subject;
  private _manager?: Manager;

  constructor(employeeId: number) {
    this.employeeId = employeeId;
  }

  get subject(): Subject {
    return this._subject;
  }

  set subject(value: Subject) {
    this._subject = value;
  }

  get manager(): Manager {
    return this._manager;
  }

  set manager(value: Manager) {
    this._manager = value;
  }
}
