import { Address } from './address';

export class Subject {
  public firstName: String;
  public middleName: String;
  public lastName: String;
  public dateOfBirth: Date;
  public position: String;
  public telephone: String;
  public email: String;
  public address: Address;

  constructor(firstName: String, middleName: String, lastName: String, dateOfBirth: Date, position: String,
              telephone: String, email: String, address: Address) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.position = position;
    this.telephone = telephone;
    this.email = email;
    this.address = address;
  }
}
