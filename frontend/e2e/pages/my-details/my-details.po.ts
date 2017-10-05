import { browser, by, element, ElementArrayFinder, ElementFinder } from 'protractor';
import { promise } from 'selenium-webdriver';
import Promise = promise.Promise;

export class MyDetailsPo {
  public navigateTo(): Promise<any> {
    return browser.get('/my-details');
  }

  public waitForAngular(): Promise<any> {
    browser.waitForAngularEnabled(true);
    return browser.waitForAngular();
  }

  public getParagraphText(): Promise<string> {
    return element(by.tagName('app-root h1')).getText();
  }

  public getPanelTitle(): ElementArrayFinder {
    return element.all(by.tagName('md-panel-title'));
  }
}
