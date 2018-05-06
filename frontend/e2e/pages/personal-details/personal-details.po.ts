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
}
