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

      it('should not display error if field is not empty', () => {
        PersonalDetailsPo.getFirstNameField().sendKeys('John');
        PersonalDetailsPo.getMiddleNameField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getFirstNameFieldWarningRequired().isPresent()).toBeFalsy();
          });
      });
    });

    describe('Middle name input field', () => {
      it('should be empty by default', () => {
        expect(PersonalDetailsPo.getMiddleNameField().getAttribute('value')).toBe('');
      });
    });

    describe('Last name input field', () => {
      it('should contain last name field with current user first name', () => {
        expect(PersonalDetailsPo.getLastNameField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getLastNameField().getAttribute('value')).toBe('Account');
      });

      it('should display error if field is empty', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getLastNameField());
        PersonalDetailsPo.getMiddleNameField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getLastNameFieldWarningRequired().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getLastNameFieldWarningRequired().getText()).toBe('Last name is required');
          });
      });

      it('should not display error if field is not empty', () => {
        PersonalDetailsPo.getLastNameField().sendKeys('Blackwell');
        PersonalDetailsPo.getMiddleNameField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getLastNameFieldWarningRequired().isPresent()).toBeFalsy();
          });
      });
    });

    describe('Date of birth input field', () => {
      it('should contain DoB field with current user dob', () => {
        expect(PersonalDetailsPo.getDateOfBirthField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getDateOfBirthField().getAttribute('value')).toBe('');
      });

      it('should display error if field is empty', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getDateOfBirthField());
        PersonalDetailsPo.getMiddleNameField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getDateOfBirthFieldWarningRequired().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getDateOfBirthFieldWarningRequired().getText()).toBe('Date of birth is required');
          });
      });

      it('should not display error if field is not empty', () => {
        PersonalDetailsPo.getDateOfBirthField().sendKeys('03/07/1990');
        PersonalDetailsPo.getMiddleNameField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getDateOfBirthFieldWarningRequired().isPresent()).toBeFalsy();
          });
      });
    });

    describe('Position input field', () => {
      it('should contain position field with current user position', () => {
        expect(PersonalDetailsPo.getPositionField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getPositionField().getAttribute('value')).toBe('');
      });
    });
  });

  describe('Contact information', () => {
    beforeAll(() => {
      PersonalDetailsPo.getPersonalInformationNextButton().click();
    });

    describe('Telephone input field', () => {
      it('should contain number field with current user telephone', () => {
        expect(PersonalDetailsPo.getTelephoneField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getTelephoneField().getAttribute('value')).toBe('');
      });
    });
  });
});
