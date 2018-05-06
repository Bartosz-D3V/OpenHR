import { browser, by, element } from 'protractor';

export class PersonalDetailsPo {
  public static getHeader() {
    return element(by.id('personal-details-header'));
  }

  static getPersonalDetailsLinkButton() {
    return element(by.id('sidenav-personal-details'));
  }

  public static getPersonalInformationTab() {
    return element(by.id('personal-details-personal-information'));
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
}
