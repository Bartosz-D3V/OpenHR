export interface Jwt {
  sub: string;
  scopes: Array<string>;
  iat: number;
  exp: number;
}
