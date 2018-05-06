import { browser, protractor } from 'protractor';

import { PersonalDetailsPo } from './personal-details.po';
import { SharedPo } from '../../shared.po';

describe('Personal details page', () => {
  beforeAll(() => {
    protractor.browser.waitForAngularEnabled(false);
    SharedPo.authenticate();
    SharedPo.waitForElement(PersonalDetailsPo.getPersonalDetailsLinkButton());
    PersonalDetailsPo.getPersonalDetailsLinkButton().click();
    browser.sleep(5000);
    SharedPo.waitForElement(PersonalDetailsPo.getHeader());
  });

  describe('Personal Information tab', () => {
    describe('First name input field', () => {
      it('should contain first name field with current user first name', () => {
        expect(PersonalDetailsPo.getFirstNameField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getFirstNameField().getAttribute('value')).toBe('HR');
      });

      it('should display error if field is empty', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getFirstNameField(), 2);
        PersonalDetailsPo.getMiddleNameField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getFirstNameFieldWarningRequired().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getFirstNameFieldWarningRequired().getText()).toBe('First name is required');
          });
      });
    });
  });
});
