import { Destination } from '../destination/destination';

export class Delegation {
  public destination: Destination;
  public selectedDays: Array<Date>;
  public budget: number;

  constructor(destination: Destination, selectedDays: Array<Date>, budget: number) {
    this.destination = destination;
    this.selectedDays = selectedDays;
    this.budget = budget;
  }
}
