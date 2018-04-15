import { WorkerData } from '@modules/core/pages/workers/worker-data';

export class WorkerDataObject implements WorkerData {
  public readonly id: number;
  public readonly name: string;
  public readonly position: string;

  constructor(id: number, name: string, position: string) {
    this.id = id;
    this.name = name;
    this.position = position;
  }
}
