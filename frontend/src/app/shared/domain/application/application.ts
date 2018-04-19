import { MomentInput } from 'moment';

export abstract class Application {
  public applicationId: number;
  public startDate: MomentInput;
  public endDate: MomentInput;
  public approvedByManager: boolean;
  public approvedByHR: boolean;
  public terminated: boolean;
  public processInstanceId: string;

  protected constructor(
    applicationId: number,
    startDate: MomentInput,
    endDate: MomentInput,
    approvedByManager: boolean,
    approvedByHR: boolean,
    terminated: boolean,
    processInstanceId: string
  ) {
    this.applicationId = applicationId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.approvedByManager = approvedByManager;
    this.approvedByHR = approvedByHR;
    this.terminated = terminated;
    this.processInstanceId = processInstanceId;
  }
}
