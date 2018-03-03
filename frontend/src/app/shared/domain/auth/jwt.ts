import {Role} from '../subject/role';

export interface Jwt {
  sub: string;
  scopes: Role;
  subjectId: number;
  iat: number;
  exp: number;
}
