import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'name',
})
export class NamePipe implements PipeTransform {
  public transform(value: String): String {
    const arr: Array<String> = value.toLowerCase().split(' ');
    for (let i = 0; i < arr.length; ++i) {
      arr[i] = arr[i]
        .charAt(0)
        .toUpperCase()
        .concat(arr[i].slice(1));
    }
    return arr.join(' ');
  }
}
