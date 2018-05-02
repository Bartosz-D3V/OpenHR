import { ObjectHelper } from '@shared/util/helpers/object-helper';
import { Employee } from '@shared/domain/subject/employee';
import { Role } from '@shared/domain/subject/role';
import { User } from '@modules/settings/pages/settings/domain/user';

describe('ObjectHelper', () => {
  const employee: Employee = new Employee(null, null, null, null, null);

  it('should remove role and repeat password properties', () => {
    employee.user = new User();
    employee.user['repeatPassword'] = 'mockPassword';
    employee.role = Role.MANAGER;
    const result = ObjectHelper.removeSubjectHelperProperties(employee);

    expect(result.user['repeatPassword']).toBeUndefined();
    expect(result.role).toBeUndefined();
  });
});
