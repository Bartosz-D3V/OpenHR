import { by, element, ElementFinder } from 'protractor';

export class PersonalDetailsPo {
  public static getHeader(): ElementFinder {
    return element(by.id('personal-details-header'));
  }

  public static getPersonalDetailsLinkButton(): ElementFinder {
    return element(by.id('sidenav-personal-details'));
  }

  public static getFirstNameField(): ElementFinder {
    return element(by.id('personal-details-first-name'));
  }

  public static getFirstNameFieldWarningRequired(): ElementFinder {
    return element(by.id('personal-details-first-name-required'));
  }

  public static getMiddleNameField(): ElementFinder {
    return element(by.id('personal-details-middle-name'));
  }

  public static getLastNameField(): ElementFinder {
    return element(by.id('personal-details-last-name'));
  }

  public static getLastNameFieldWarningRequired(): ElementFinder {
    return element(by.id('personal-details-last-name-required'));
  }

  public static getDateOfBirthField(): ElementFinder {
    return element(by.id('personal-details-date-of-birth'));
  }

  public static getDateOfBirthFieldWarningRequired(): ElementFinder {
    return element(by.id('personal-details-date-of-birth-required'));
  }

  public static getPersonalInformationNextButton(): ElementFinder {
    return element(by.id('personal-details-personal-information-next'));
  }

  public static getTelephoneField(): ElementFinder {
    return element(by.id('personal-details-telephone'));
  }

  public static getTelephoneFieldWarningMin(): ElementFinder {
    return element(by.id('personal-details-telephone-min'));
  }

  public static getTelephoneFieldWarningMax(): ElementFinder {
    return element(by.id('personal-details-telephone-max'));
  }

  public static getTelephoneFieldWarningRequired(): ElementFinder {
    return element(by.id('personal-details-telephone-required'));
  }

  public static getTelephoneFieldWarningPattern(): ElementFinder {
    return element(by.id('personal-details-telephone-pattern'));
  }

  public static getEmailField(): ElementFinder {
    return element(by.id('personal-details-email'));
  }

  public static getEmailFieldWarningRequired(): ElementFinder {
    return element(by.id('personal-details-email-required'));
  }

  public static getEmailFieldWarningPattern(): ElementFinder {
    return element(by.id('personal-details-email-pattern'));
  }

  public static getFirstLineAddressField(): ElementFinder {
    return element(by.id('personal-details-1st-line-address'));
  }

  public static getSecondLineAddressField(): ElementFinder {
    return element(by.id('personal-details-2nd-line-address'));
  }

  public static getThirdLineAddressField(): ElementFinder {
    return element(by.id('personal-details-3rd-line-address'));
  }

  public static getPostcodeField(): ElementFinder {
    return element(by.id('personal-details-postcode'));
  }

  public static getPostcodeFieldWarningPattern(): ElementFinder {
    return element(by.id('personal-details-postcode-pattern'));
  }

  public static getPostcodeFieldWarningRequired(): ElementFinder {
    return element(by.id('personal-details-postcode-required'));
  }

  public static getCityField(): ElementFinder {
    return element(by.id('personal-details-city'));
  }

  public static getCountryField(): ElementFinder {
    return element(by.id('personal-details-country'));
  }

  public static getContactInformationNextButton(): ElementFinder {
    return element(by.id('personal-details-contact-information-next'));
  }

  public static getNINField(): ElementFinder {
    return element(by.id('personal-details-nin'));
  }

  public static getNINFieldWarningPattern(): ElementFinder {
    return element(by.id('personal-details-nin-pattern'));
  }

  public static getNINFieldWarningRequired(): ElementFinder {
    return element(by.id('personal-details-nin-required'));
  }

  public static getEmployeeIDField(): ElementFinder {
    return element(by.id('personal-details-employee-number'));
  }

  public static getEmployeeIDFieldWarningRequired(): ElementFinder {
    return element(by.id('personal-details-employee-number-required'));
  }

  public static getPositionField(): ElementFinder {
    return element(by.id('personal-details-position'));
  }

  public static getPositionFieldWarningRequired(): ElementFinder {
    return element(by.id('personal-details-position-required'));
  }

  public static getStartDateField(): ElementFinder {
    return element(by.id('personal-details-start-date'));
  }

  public static getEndDateField(): ElementFinder {
    return element(by.id('personal-details-end-date'));
  }

  public static getEmployeeInformationNextButton(): ElementFinder {
    return element(by.id('personal-details-employee-information-next'));
  }

  public static getAllowanceField(): ElementFinder {
    return element(by.id('personal-details-allowance'));
  }

  public static getAllowanceFieldWarningMin(): ElementFinder {
    return element(by.id('personal-details-allowance-min'));
  }

  public static getUsedAllowanceField(): ElementFinder {
    return element(by.id('personal-details-used-allowance'));
  }

  public static getUsedAllowanceFieldWarningMin(): ElementFinder {
    return element(by.id('personal-details-used-allowance-min'));
  }

  public static getSubmitButton(): ElementFinder {
    return element(by.id('personal-details-submit-button'));
  }
}
