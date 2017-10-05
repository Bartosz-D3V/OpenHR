import { browser, by, element, ElementFinder } from 'protractor';
import { promise } from 'selenium-webdriver';
import Promise = promise.Promise;

export class MyDetailsPo {
  navigateTo(): Promise<any> {
    return browser.get('/my-details');
  }

  waitForAngular(): Promise<any> {
    return browser.waitForAngular();
  }

  getParagraphText(): Promise<string> {
    return element(by.css('app-root h1')).getText();
  }

  getAppPageHeader(): ElementFinder {
    return element(by.tagName('app-page-header'));
  }
}
