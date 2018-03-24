import { Role } from '../subject/role';

export interface Jwt {
  sub: string;
  scopes: Array<Role>;
  subjectId: number;
  iat: number;
  exp: number;
}
