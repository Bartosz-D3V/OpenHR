import { Pipe, PipeTransform } from '@angular/core';
import { Application } from '@shared/domain/application/application';

@Pipe({
  name: 'applicationType',
})
export class ApplicationTypePipe implements PipeTransform {
  transform(value: Application): string {
    return value.hasOwnProperty('leaveType') ? 'Leave application' : value.hasOwnProperty('budget') ? 'Delegation application' : null;
  }
}
