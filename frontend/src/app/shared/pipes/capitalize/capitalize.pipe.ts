import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'capitalize'
})
export class CapitalizePipe implements PipeTransform {

  transform(value: String): String {
    return value.charAt(0).toUpperCase() + value.slice(1);
  }

}
