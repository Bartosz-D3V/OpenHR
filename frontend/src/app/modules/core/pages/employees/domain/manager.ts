import { Subject } from '../../../../../shared/domain/subject/subject';
import { Employee } from './employee';

export class Manager {
  public managerId: number;
  public subject?: Subject;
  public employees?: Set<Employee>;
}
