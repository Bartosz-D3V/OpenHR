import {EmployeeData} from '../employee-data';

export class EmployeeDataObject implements EmployeeData {
  id: number;
  name: string;
  position: string;

  constructor(id: number, name: string, position: string) {
    this.id = id;
    this.name = name;
    this.position = position;
  }
}
