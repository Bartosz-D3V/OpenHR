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
    expect(pageHeader.getText()).toEqual('My details');
  });

  describe('panel titles', () => {

    it('should have a panel title with name "Personal information"', () => {
      const panelTitle: ElementFinder = page.getPanelTitle().get(0);

      expect(panelTitle).toBeDefined();
      expect(panelTitle.isDisplayed()).toBeTruthy();
      expect(panelTitle.getText()).toEqual('Personal information');
    });

  });
});
