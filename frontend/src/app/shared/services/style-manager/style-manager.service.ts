import { Injectable } from '@angular/core';

@Injectable()
export class StyleManagerService {
  public getExistingLinkElementByKey(key: string) {
    return document.head.querySelector(`link[rel="stylesheet"].${this.getClassNameForKey(key)}`);
  }

  private getClassNameForKey(key: string) {
    return `style-manager-${key}`;
  }
}
