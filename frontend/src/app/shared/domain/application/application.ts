import { MomentInput } from 'moment';

import { ApplicationType } from '@shared/constants/enumeration/application-type';

export abstract class Application {
  public applicationId: number;
  public startDate: MomentInput;
  public endDate: MomentInput;
  public approvedByManager: boolean;
  public approvedByHR: boolean;
  public terminated: boolean;
  public processInstanceId: string;
  public applicationType?: ApplicationType;
  public refusalReason?: string;
  public subjectId?: number;

  protected constructor(
    applicationId: number,
    startDate: MomentInput,
    endDate: MomentInput,
    approvedByManager: boolean,
    approvedByHR: boolean,
    terminated: boolean,
    processInstanceId: string,
    applicationType?: ApplicationType,
    refusalReason?: string,
    subjectId?: number
  ) {
    this.applicationId = applicationId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.approvedByManager = approvedByManager;
    this.approvedByHR = approvedByHR;
    this.terminated = terminated;
    this.processInstanceId = processInstanceId;
    this.applicationType = applicationType ? applicationType : null;
    this.refusalReason = refusalReason ? refusalReason : null;
    this.subjectId = subjectId ? subjectId : null;
  }
}
