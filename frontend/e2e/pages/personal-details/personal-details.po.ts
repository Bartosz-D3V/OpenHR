import { by, element } from 'protractor';

export class PersonalDetailsPo {
  public static getHeader() {
    return element(by.id('personal-details-header'));
  }

  static getPersonalDetailsLinkButton() {
    return element(by.id('sidenav-personal-details'));
  }

  public static getFirstNameField() {
    return element(by.id('personal-details-first-name'));
  }

  public static getFirstNameFieldWarningRequired() {
    return element(by.id('personal-details-first-name-required'));
  }

  public static getMiddleNameField() {
    return element(by.id('personal-details-middle-name'));
  }

  public static getLastNameField() {
    return element(by.id('personal-details-last-name'));
  }

  public static getLastNameFieldWarningRequired() {
    return element(by.id('personal-details-last-name-required'));
  }

  public static getDateOfBirthField() {
    return element(by.id('personal-details-date-of-birth'));
  }

  public static getDateOfBirthFieldWarningRequired() {
    return element(by.id('personal-details-date-of-birth-required'));
  }

  public static getPositionField() {
    return element(by.id('personal-details-position'));
  }

  public static getPersonalInformationNextButton() {
    return element(by.id('personal-details-personal-information-next'));
  }

  public static getTelephoneField() {
    return element(by.id('personal-details-telephone'));
  }

  public static getTelephoneFieldWarningMin() {
    return element(by.id('personal-details-telephone-min'));
  }

  public static getTelephoneFieldWarningMax() {
    return element(by.id('personal-details-telephone-max'));
  }

  public static getTelephoneFieldWarningRequired() {
    return element(by.id('personal-details-telephone-required'));
  }

  public static getTelephoneFieldWarningPattern() {
    return element(by.id('personal-details-telephone-pattern'));
  }

  public static getEmailField() {
    return element(by.id('personal-details-email'));
  }

  public static getEmailFieldWarningRequired() {
    return element(by.id('personal-details-email-required'));
  }

  public static getEmailFieldWarningPattern() {
    return element(by.id('personal-details-email-pattern'));
  }

  public static getFirstLineAddressField() {
    return element(by.id('personal-details-1st-line-address'));
  }

  public static getSecondLineAddressField() {
    return element(by.id('personal-details-2nd-line-address'));
  }

  public static getThirdLineAddressField() {
    return element(by.id('personal-details-3rd-line-address'));
  }

  public static getPostcodeField() {
    return element(by.id('personal-details-postcode'));
  }

  public static getPostcodeFieldWarningPattern() {
    return element(by.id('personal-details-postcode-pattern'));
  }

  public static getPostcodeFieldWarningRequired() {
    return element(by.id('personal-details-postcode-required'));
  }

  public static getCityField() {
    return element(by.id('personal-details-city'));
  }

  public static getCountryField() {
    return element(by.id('personal-details-country'));
  }

  public static getContactInformationNextButton() {
    return element(by.id('personal-details-contact-information-next'));
  }

  public static getNINField() {
    return element(by.id('personal-details-nin'));
  }

  public static getNINFieldWarningPattern() {
    return element(by.id('personal-details-nin-pattern'));
  }

  public static getNINFieldWarningRequired() {
    return element(by.id('personal-details-nin-required'));
  }

  public static getEmployeeIDField() {
    return element(by.id('personal-details-employee-number'));
  }

  public static getEmployeeIDFieldWarningRequired() {
    return element(by.id('personal-details-employee-number-required'));
  }

  public static getStartDateField() {
    return element(by.id('personal-details-start-date'));
  }

  public static getEndDateField() {
    return element(by.id('personal-details-end-date'));
  }

  public static getEmployeeInformationNextButton() {
    return element(by.id('personal-details-employee-information-next'));
  }

  public static getAllowanceField() {
    return element(by.id('personal-details-allowance'));
  }

  public static getAllowanceFieldWarningMin() {
    return element(by.id('personal-details-allowance-min'));
  }

  public static getUsedAllowanceField() {
    return element(by.id('personal-details-used-allowance'));
  }

  public static getUsedAllowanceFieldWarningMin() {
    return element(by.id('personal-details-used-allowance-min'));
  }

  public static getSubmitButton() {
    return element(by.id('personal-details-submit-button'));
  }
}
