import { protractor } from 'protractor';

import { PersonalDetailsPo } from './personal-details.po';
import { SharedPo } from '../../shared.po';

describe('Personal details page', () => {
  beforeAll(() => {
    SharedPo.authenticate();
  });

  beforeEach(() => {
    protractor.browser.waitForAngularEnabled(true);
    PersonalDetailsPo.getPersonalDetailsLinkButton().click();
  });

  describe('Personal Information tab', () => {
    beforeAll(() => {
      PersonalDetailsPo.getPersonalInformationTab().click();
    });

    describe('First name input field', () => {
      it('should contain first name field with current user first name', () => {
        expect(PersonalDetailsPo.getFirstNameField().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getFirstNameField().getText()).toBe('');
      });

      it('should display error if field is empty', () => {
        PersonalDetailsPo.getFirstNameField().click();
        PersonalDetailsPo.getMiddleNameField().click();

        expect(PersonalDetailsPo.getFirstNameFieldWarningRequired().isPresent()).toBeTruthy();
        expect(PersonalDetailsPo.getFirstNameFieldWarningRequired().getText()).toBe('First name is required');
      });
    });
  });
});
