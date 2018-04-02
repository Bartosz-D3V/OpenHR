import { Pipe, PipeTransform } from '@angular/core';

import { Application } from '@shared/domain/application/application';
import { ApplicationStatuses } from '@modules/core/pages/my-applications/enumeration/application-statuses.enum';

@Pipe({
  name: 'applicationStatus',
})
export class ApplicationStatusPipe implements PipeTransform {
  transform(application: Application): ApplicationStatuses {
    if (application.terminated) {
      return !application.approvedByManager
        ? ApplicationStatuses.REJECTEDBYMANAGER
        : !application.approvedByHR ? ApplicationStatuses.REJECTEDBYHR : ApplicationStatuses.ACCEPTED;
    }
    return ApplicationStatuses.AWAITING;
  }
}
