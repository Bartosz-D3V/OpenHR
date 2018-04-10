import { UserRole } from '@shared/domain/user/user-role';

export class User {
  public userId: number;
  public username: string;
  public notificationsTurnedOn: boolean;
  public userRoles: Array<UserRole>;

  constructor() {}
}
