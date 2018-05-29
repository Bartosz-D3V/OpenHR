import { browser, by, element, ElementFinder, promise, protractor } from 'protractor';

export class SharedPo {
  public static authenticate(): any {
    browser.get('/login');
    element(by.id('login-page-username')).sendKeys('hr');
    element(by.id('login-page-password')).sendKeys(1234);
    element(by.id('login-page-submit')).click();
    browser.sleep(2500);
  }

  public static waitForElement(el: any): any {
    return browser.wait(protractor.ExpectedConditions.presenceOf(el), 9000);
  }

  public static resetValueByKeyboard(elem: ElementFinder, length?: number): promise.Promise<void> {
    length = length || 100;
    let backspaceSeries = '';
    for (let i = 0; i < length; i++) {
      backspaceSeries += protractor.Key.BACK_SPACE;
    }
    return elem.sendKeys(backspaceSeries);
  }
}
