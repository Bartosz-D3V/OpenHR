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
  });

  describe('Contact information', () => {
    beforeAll(() => {
      PersonalDetailsPo.getPersonalInformationNextButton().click();
    });

    describe('Telephone input field', () => {
      it('should contain number field with current user telephone', () => {
        expect(PersonalDetailsPo.getTelephoneField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getTelephoneField().getText()).toBe('');
      });

      it('should display error if number of digits is less than 7', () => {
        PersonalDetailsPo.getTelephoneField().sendKeys('123456');
        PersonalDetailsPo.getEmailField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getTelephoneFieldWarningMin().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getTelephoneFieldWarningMin().getText()).toBe('Telephone number must contains at least 7 digits');
          });
      });

      it('should display error if number of digits is greater than 11', () => {
        PersonalDetailsPo.getTelephoneField().sendKeys('123456789123');
        PersonalDetailsPo.getEmailField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getTelephoneFieldWarningMax().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getTelephoneFieldWarningMax().getText()).toBe('Telephone number must contains maximum 11 digits');
          });
      });

      it('should display error if number is missing', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getTelephoneField());
        PersonalDetailsPo.getEmailField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getTelephoneFieldWarningRequired().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getTelephoneFieldWarningRequired().getText()).toBe('Telephone is required');
          });
      });

      it('should display error if number is not valid', () => {
        PersonalDetailsPo.getTelephoneField().sendKeys('abcd-172-fg');
        PersonalDetailsPo.getEmailField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getTelephoneFieldWarningPattern().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getTelephoneFieldWarningPattern().getText()).toBe('Please enter a valid telephone');
          });
      });

      it('should not display error if number is correct', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getTelephoneField());
        PersonalDetailsPo.getTelephoneField().sendKeys('159875365');
        PersonalDetailsPo.getEmailField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getTelephoneFieldWarningRequired().isPresent()).toBeFalsy();
            expect(PersonalDetailsPo.getTelephoneFieldWarningMax().isPresent()).toBeFalsy();
            expect(PersonalDetailsPo.getTelephoneFieldWarningMin().isPresent()).toBeFalsy();
            expect(PersonalDetailsPo.getTelephoneFieldWarningPattern().isPresent()).toBeFalsy();
          });
      });
    });

    describe('Email input field', () => {
      it('should contain number field with current user email', () => {
        expect(PersonalDetailsPo.getEmailField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getEmailField().getText()).toBe('');
      });

      it('should display error if email is not correct', () => {
        PersonalDetailsPo.getEmailField().sendKeys('test@.');
        PersonalDetailsPo.getTelephoneField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getEmailFieldWarningPattern().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getEmailFieldWarningPattern().getText()).toBe('Please enter a valid email address');
          });
      });

      it('should display error if email is missing', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getEmailField());
        PersonalDetailsPo.getTelephoneField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getEmailFieldWarningRequired().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getEmailFieldWarningRequired().getText()).toBe('Email is required');
          });
      });

      it('should not display error if email is correct', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getEmailField());
        PersonalDetailsPo.getEmailField().sendKeys('test@e2e-angular.org');
        PersonalDetailsPo.getTelephoneField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getEmailFieldWarningRequired().isPresent()).toBeFalsy();
            expect(PersonalDetailsPo.getEmailFieldWarningPattern().isPresent()).toBeFalsy();
          });
      });
    });

    describe('address', () => {
      describe('1st line input field', () => {
        it('should contain text field with current user 1st line address', () => {
          expect(PersonalDetailsPo.getFirstLineAddressField().isPresent()).toBeTruthy();
          expect(PersonalDetailsPo.getFirstLineAddressField().getText()).toBe('');
        });
      });

      describe('2nd line input field', () => {
        it('should contain text field with current user 2nd line address', () => {
          expect(PersonalDetailsPo.getSecondLineAddressField().isPresent()).toBeTruthy();
          expect(PersonalDetailsPo.getSecondLineAddressField().getText()).toBe('');
        });
      });

      describe('3r line input field', () => {
        it('should contain text field with current user 3rd line address', () => {
          expect(PersonalDetailsPo.getThirdLineAddressField().isPresent()).toBeTruthy();
          expect(PersonalDetailsPo.getThirdLineAddressField().getText()).toBe('');
        });
      });

      describe('postcode input field', () => {
        it('should contain text field with current user postcode', () => {
          expect(PersonalDetailsPo.getPostcodeField().isPresent()).toBeTruthy();
          expect(PersonalDetailsPo.getPostcodeField().getText()).toBe('');
        });

        it('should display error if postcode format is not correct', () => {
          PersonalDetailsPo.getPostcodeField().sendKeys('12 BG');
          PersonalDetailsPo.getEmailField()
            .click()
            .then(() => {
              expect(PersonalDetailsPo.getPostcodeFieldWarningPattern().isPresent()).toBeTruthy();
              expect(PersonalDetailsPo.getPostcodeFieldWarningPattern().getText()).toBe('Please enter a valid postcode');
            });
        });

        it('should display error if postcode is missing', () => {
          SharedPo.resetValueByKeyboard(PersonalDetailsPo.getPostcodeField());
          PersonalDetailsPo.getEmailField()
            .click()
            .then(() => {
              expect(PersonalDetailsPo.getPostcodeFieldWarningRequired().isPresent()).toBeTruthy();
              expect(PersonalDetailsPo.getPostcodeFieldWarningRequired().getText()).toBe('Postcode is required');
            });
        });

        it('should not display error if postcode format is correct', () => {
          PersonalDetailsPo.getPostcodeField().sendKeys('SP2 8BE');
          PersonalDetailsPo.getEmailField()
            .click()
            .then(() => {
              expect(PersonalDetailsPo.getPostcodeFieldWarningPattern().isPresent()).toBeFalsy();
              expect(PersonalDetailsPo.getPostcodeFieldWarningRequired().isPresent()).toBeFalsy();
            });
        });
      });

      describe('city input field', () => {
        it('should contain text field with current user country', () => {
          expect(PersonalDetailsPo.getCityField().isPresent()).toBeTruthy();
          expect(PersonalDetailsPo.getCityField().getText()).toBe('');
        });
      });

      describe('country input field', () => {
        it('should contain text field with current user country', () => {
          expect(PersonalDetailsPo.getCountryField().isPresent()).toBeTruthy();
          expect(PersonalDetailsPo.getCountryField().getText()).toBe('');
        });
      });
    });
  });

  describe('Employee information', () => {
    beforeAll(() => {
      PersonalDetailsPo.getContactInformationNextButton().click();
    });

    describe('NIN field', () => {
      it('should contain text field with current user nin', () => {
        expect(PersonalDetailsPo.getNINField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getNINField().getText()).toBe('');
      });

      it('should display error if nin is not correct', () => {
        PersonalDetailsPo.getNINField().sendKeys('12 HJ 88');
        PersonalDetailsPo.getEmployeeIDField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getNINFieldWarningPattern().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getNINFieldWarningPattern().getText()).toBe('NIN is not correct');
          });
      });

      it('should display error if nin is missing', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getNINField());
        PersonalDetailsPo.getEmployeeIDField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getNINFieldWarningRequired().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getNINFieldWarningRequired().getText()).toBe('NIN is required');
          });
      });

      it('should not display error if nin is correct', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getNINField());
        PersonalDetailsPo.getNINField().sendKeys('AX 60 93 31 B');
        PersonalDetailsPo.getEmployeeIDField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getNINFieldWarningRequired().isPresent()).toBeFalsy();
            expect(PersonalDetailsPo.getNINFieldWarningPattern().isPresent()).toBeFalsy();
          });
      });
    });

    describe('Employee number', () => {
      it('should contain text field with current user employee number', () => {
        expect(PersonalDetailsPo.getEmployeeIDField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getEmployeeIDField().getText()).toBe('');
      });

      it('should display error if employee number is missing', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getEmployeeIDField());
        PersonalDetailsPo.getNINField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getEmployeeIDFieldWarningRequired().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getEmployeeIDFieldWarningRequired().getText()).toBe('Employee number is required');
          });
      });

      it('should not display error if employee number is present', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getEmployeeIDField());
        PersonalDetailsPo.getEmployeeIDField().sendKeys('12X A');
        PersonalDetailsPo.getNINField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getEmployeeIDFieldWarningRequired().isPresent()).toBeFalsy();
          });
      });
    });

    describe('Position input field', () => {
      it('should contain position field with current user position', () => {
        expect(PersonalDetailsPo.getPositionField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getPositionField().getAttribute('value')).toBe('');
      });

      it('should display error if position is missing', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getPositionField());
        PersonalDetailsPo.getEmployeeIDField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getPositionFieldWarningRequired().isPresent()).toBeTruthy();
            expect(PersonalDetailsPo.getPositionFieldWarningRequired().getText()).toBe('Position is required');
          });
      });

      it('should not display error if position is provided', () => {
        SharedPo.resetValueByKeyboard(PersonalDetailsPo.getPositionField());
        PersonalDetailsPo.getPositionField().sendKeys('Automation tester');
        PersonalDetailsPo.getEmployeeIDField()
          .click()
          .then(() => {
            expect(PersonalDetailsPo.getPositionFieldWarningRequired().isPresent()).toBeFalsy();
          });
      });
    });

    describe('start date input field', () => {
      it('should contain date field with current user start date', () => {
        expect(PersonalDetailsPo.getStartDateField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getStartDateField().getText()).toBe('');
      });
    });

    describe('end date input field', () => {
      it('should contain date field with current user end date', () => {
        expect(PersonalDetailsPo.getEndDateField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getEndDateField().getText()).toBe('');
      });
    });
  });

  describe('HR information', () => {
    beforeAll(() => {
      PersonalDetailsPo.getEmployeeInformationNextButton();
    });

    describe('allowance field', () => {
      it('should contain number field with current user allowance', () => {
        expect(PersonalDetailsPo.getAllowanceField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getAllowanceField().getText()).toBe('');
      });

      it('should not be editable', () => {
        expect(PersonalDetailsPo.getAllowanceField().isEnabled()).toBeFalsy();
      });
    });

    describe('used allowance field', () => {
      it('should contain number field with current user allowance', () => {
        expect(PersonalDetailsPo.getUsedAllowanceField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getUsedAllowanceField().getText()).toBe('');
      });

      it('should not be editable', () => {
        expect(PersonalDetailsPo.getUsedAllowanceField().isEnabled()).toBeFalsy();
      });
    });
  });
});
