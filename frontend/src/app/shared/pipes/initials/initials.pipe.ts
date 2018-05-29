import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'initials',
})
export class InitialsPipe implements PipeTransform {
  private readonly SINGLE_SPACE: string = ' ';

  public transform(fullName: string): string {
    if (fullName !== null) {
      const splittedName: Array<string> = fullName.trim().split(this.SINGLE_SPACE);

      return splittedName[0]
        .charAt(0)
        .concat(splittedName[splittedName.length - 1].charAt(0))
        .toUpperCase();
    }
  }
}
