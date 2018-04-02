export class Address {
  public firstLineAddress: String;
  public secondLineAddress: String;
  public thirdLineAddress: String;
  public postcode: String;
  public city: String;
  public country: String;

  constructor(
    firstLineAddress: String,
    secondLineAddress: String,
    thirdLineAddress: String,
    postcode: String,
    city: String,
    country: String
  ) {
    this.firstLineAddress = firstLineAddress;
    this.secondLineAddress = secondLineAddress;
    this.thirdLineAddress = thirdLineAddress;
    this.postcode = postcode;
    this.city = city;
    this.country = country;
  }
}
