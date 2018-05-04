import { browser, by, element } from 'protractor';

export class LoginPo {
  public static navigateToLoginPage() {
    return browser.get('/login');
  }

  public static getPageTitle() {
    return element(by.css('.c-login-box__title'));
  }

  public static getUsernameInput() {
    return element(by.id('login-page-username'));
  }

  public static getPasswordInput() {
    return element(by.id('login-page-password'));
  }

  public static getUsernameWarning() {
    return element(by.id('login-page-username-error-required'));
  }

  public static getPasswordWarningRequired() {
    return element(by.id('login-page-password-error-required'));
  }

  public static getPasswordWarningInvalid() {
    return element(by.id('login-page-password-error-invalid'));
  }

  public static getLoginButton() {
    return element(by.id('login-page-submit'));
  }
}
