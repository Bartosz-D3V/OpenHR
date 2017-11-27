export class RegularExpressions {
  public static UK_POSTCODE: RegExp = new RegExp(/^[A-Z]{1,2}[0-9]{1,2} ?[0-9][A-Z]{2}$/i);
  public static EMAIL: RegExp = new RegExp(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/);
  public static NUMBERS_ONLY: RegExp = new RegExp(/^[0-9]+$/);
  public static LETTERS_ONLY: RegExp = new RegExp('^[a-zA-Z]*$');
  public static NIN: RegExp = new RegExp(/^(?!BG|GB|NK|KN|TN|NT|ZZ)[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z](?:\s*\d{2}){3}\s*[A-D]$/);
}
