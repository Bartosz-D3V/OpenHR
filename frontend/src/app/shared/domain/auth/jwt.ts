export interface Jwt {
  sub: string;
  scopes: Array<string>;
  subjectId: number;
  iat: number;
  exp: number;
}
