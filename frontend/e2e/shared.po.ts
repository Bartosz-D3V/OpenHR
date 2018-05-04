import { browser, by, element } from 'protractor';

export class SharedPo {
  public static authenticate() {
    browser.get('/login');
    element(by.id('login-page-username')).sendKeys('hr');
    element(by.id('login-page-password')).sendKeys(1234);
    element(by.id('login-page-submit')).click();
    browser.sleep(2500);
  }
}
