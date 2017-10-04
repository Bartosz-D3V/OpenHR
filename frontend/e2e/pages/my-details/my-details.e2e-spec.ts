import { MyDetailsPo } from './my-details.po';

describe('My details page', () => {
  let page: MyDetailsPo;

  beforeEach(() => {
    page = new MyDetailsPo();
    page.navigateTo();
  });

  it('should have a header module', () => {
    const pageHeader = page.getAppPageHeader();

    expect(pageHeader).toBeDefined();
    expect(pageHeader.isDisplayed()).toBeTruthy();
    expect(pageHeader.getWebElement().getText()).toEqual('My details');
  });
});
