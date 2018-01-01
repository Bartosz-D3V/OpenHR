import { Address } from './address';

export class ContactInformation {
  public telephone: string;
  public email: string;
  public address: Address;

  constructor(telephone: string, email: string, address: Address) {
    this.telephone = telephone;
    this.email = email;
    this.address = address;
  }
}
