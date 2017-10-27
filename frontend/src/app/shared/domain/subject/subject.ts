import { Address } from './address';

export class Subject {
  public firstName: string;
  public middleName: string;
  public lastName: string;
  public fullName: string;
  public dateOfBirth: Date;
  public position: string;
  public telephone: string;
  public email: string;
  public address: Address;

  constructor(firstName: string, lastName: string, dateOfBirth: Date, position: string,
              telephone: string, email: string, address: Address, middleName?: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.fullName = firstName + ' ' + lastName;
    this.dateOfBirth = dateOfBirth;
    this.position = position;
    this.telephone = telephone;
    this.email = email;
    this.address = address;
    this.middleName = middleName ? middleName : null;
  }
}
