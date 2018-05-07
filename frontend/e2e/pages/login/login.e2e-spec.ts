import { browser, protractor } from 'protractor';

import { LoginPo } from './login.po';

describe('Login page', () => {
  beforeEach(() => {
    LoginPo.navigateToLoginPage();
    protractor.browser.waitForAngularEnabled(true);
  });

  describe('main component', () => {
    it('should have the login title and two input fields', () => {
      expect(LoginPo.getPageTitle().getText()).toBe('Login');
      expect(LoginPo.getUsernameInput().getAttribute('placeholder')).toBe('Username');
      expect(LoginPo.getPasswordInput().getAttribute('placeholder')).toBe('Password');
    });

    it('should display warning if username is empty', () => {
      expect(LoginPo.getUsernameWarning().isPresent()).toBeFalsy();

      LoginPo.getUsernameInput().click();
      LoginPo.getPasswordInput().click();

      expect(LoginPo.getUsernameWarning().getText()).toBe('Username is required');
      expect(LoginPo.getUsernameWarning().isPresent()).toBeTruthy();
    });

    it('should display warning if password is empty', () => {
      expect(LoginPo.getPasswordWarningRequired().isPresent()).toBeFalsy();

      LoginPo.getPasswordInput().click();
      LoginPo.getUsernameInput().click();

      expect(LoginPo.getPasswordWarningRequired().getText()).toBe('Password is required');
      expect(LoginPo.getPasswordWarningRequired().isPresent()).toBeTruthy();
    });

    it('should display warning if login and password is not valid', () => {
      LoginPo.getUsernameInput().sendKeys('invalid-credentials');
      LoginPo.getPasswordInput().sendKeys('invalid-credentials');
      LoginPo.getLoginButton().click();

      expect(LoginPo.getPasswordWarningInvalid().isPresent()).toBeTruthy();
      expect(LoginPo.getPasswordWarningInvalid().getText()).toBe('Credentials are invalid');
    });

    it('should redirect to dashboard if credentials are valid', () => {
      LoginPo.getUsernameInput().sendKeys('hr');
      LoginPo.getPasswordInput().sendKeys('1234');
      LoginPo.getLoginButton().click();
      browser.sleep(2500);

      expect(browser.driver.getCurrentUrl()).toContain('/app/(core:dashboard)');
    });
  });
});
