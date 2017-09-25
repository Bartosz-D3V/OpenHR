import { RegularExpressions } from './regular-expressions';

describe('RegularExpressions', () => {

  it('should be defined', () => {
    expect(RegularExpressions).toBeDefined();
  });

  describe('UK_POSTCODE', () => {
    it('should accept a valid UK postcode', () => {
      const exampleValidPostcode1 = 'CR2 6XH';
      const exampleValidPostcode2 = 'M1 1AE';
      const exampleValidPostcode3 = 'DN55 1PT';

      expect(RegularExpressions.UK_POSTCODE.test(exampleValidPostcode1)).toBeTruthy();
      expect(RegularExpressions.UK_POSTCODE.test(exampleValidPostcode2)).toBeTruthy();
      expect(RegularExpressions.UK_POSTCODE.test(exampleValidPostcode3)).toBeTruthy();
    });

    it('should not accept a valid UK postcode', () => {
      const exampleInvalidPostcode1 = '1 2A';
      const exampleInvalidPostcode2 = '22 UX';
      const exampleInvalidPostcode3 = 'WWW FGH';

      expect(RegularExpressions.UK_POSTCODE.test(exampleInvalidPostcode1)).toBeFalsy();
      expect(RegularExpressions.UK_POSTCODE.test(exampleInvalidPostcode2)).toBeFalsy();
      expect(RegularExpressions.UK_POSTCODE.test(exampleInvalidPostcode3)).toBeFalsy();
    });
  });

  describe('EMAIL', () => {
    it('should accept a valid email address', () => {
      const exampleValidEmail1 = 'test@test.com';
      const exampleValidEmail2 = 'test@test.org.com';
      const exampleValidEmail3 = 'test.test@co.uk';

      expect(RegularExpressions.EMAIL.test(exampleValidEmail1)).toBeTruthy();
      expect(RegularExpressions.EMAIL.test(exampleValidEmail2)).toBeTruthy();
      expect(RegularExpressions.EMAIL.test(exampleValidEmail3)).toBeTruthy();
    });

    it('should not accept a valid email address', () => {
      const exampleInvalidEmail1 = 'test@';
      const exampleInvalidEmail2 = '@test.org.com';
      const exampleInvalidEmail3 = 'test.test';

      expect(RegularExpressions.EMAIL.test(exampleInvalidEmail1)).toBeFalsy();
      expect(RegularExpressions.EMAIL.test(exampleInvalidEmail2)).toBeFalsy();
      expect(RegularExpressions.EMAIL.test(exampleInvalidEmail3)).toBeFalsy();
    });
  });

  describe('NUMBERS_ONLY', () => {
    it('should accept numbers only', () => {
      const exampleNumber1 = '1233322';
      const exampleNumber2 = '21556';

      expect(RegularExpressions.NUMBERS_ONLY.test(exampleNumber1)).toBeTruthy();
      expect(RegularExpressions.NUMBERS_ONLY.test(exampleNumber2)).toBeTruthy();
    });

    it('should accept numbers only', () => {
      const exampleInNumber1 = '1sda233322';
      const exampleInNumber2 = '21556abv';

      expect(RegularExpressions.NUMBERS_ONLY.test(exampleInNumber1)).toBeFalsy();
      expect(RegularExpressions.NUMBERS_ONLY.test(exampleInNumber2)).toBeFalsy();
    });
  });
});
