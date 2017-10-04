import { browser, by, element } from 'protractor';

export class MyDetailsPo {
  navigateTo() {
    return browser.get('/my-details');
  }

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }

  getAppPageHeader() {
    return element(by.name('app-page-header'));
  }
}
