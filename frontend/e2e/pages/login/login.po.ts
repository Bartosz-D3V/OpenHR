import { browser, by, element, ElementFinder, promise } from 'protractor';

export class LoginPo {
  public static navigateToLoginPage(): promise.Promise<any> {
    return browser.get('/login');
  }

  public static getPageTitle(): ElementFinder {
    return element(by.css('.c-login-box__title'));
  }

  public static getUsernameInput(): ElementFinder {
    return element(by.id('login-page-username'));
  }

  public static getPasswordInput(): ElementFinder {
    return element(by.id('login-page-password'));
  }

  public static getUsernameWarning(): ElementFinder {
    return element(by.id('login-page-username-error-required'));
  }

  public static getPasswordWarningRequired(): ElementFinder {
    return element(by.id('login-page-password-error-required'));
  }

  public static getPasswordWarningInvalid(): ElementFinder {
    return element(by.id('login-page-password-error-invalid'));
  }

  public static getLoginButton(): ElementFinder {
    return element(by.id('login-page-submit'));
  }
}
