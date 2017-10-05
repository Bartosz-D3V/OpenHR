import { MyDetailsPo } from './my-details.po';
import { ElementFinder } from 'protractor';

describe('My details page', () => {
  let page: MyDetailsPo;

  beforeEach(() => {
    page = new MyDetailsPo();
    page.navigateTo();
    page.waitForAngular();
  });

  it('should have a header module', () => {
    const pageHeader: ElementFinder = page.getAppPageHeader();

    expect(pageHeader).toBeDefined();
    expect(pageHeader.isDisplayed()).toBeTruthy();
    expect(pageHeader.getWebElement().getText()).toEqual('My details');
  });
});
