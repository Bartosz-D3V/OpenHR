import { Pipe, PipeTransform } from '@angular/core';

import { Application } from '@shared/domain/application/application';
import { ApplicationType } from '@shared/constants/enumeration/application-type';

@Pipe({
  name: 'applicationType',
})
export class ApplicationTypePipe implements PipeTransform {
  public transform(value: Application): string {
    return value.applicationType === ApplicationType.LEAVE_APPLICATION
      ? 'Leave application'
      : value.applicationType === ApplicationType.DELEGATION_APPLICATION
        ? 'Delegation application'
        : null;
  }
}
