import { Subject } from './subject';
import { Address } from './address';

describe('Subject constructor', () => {

  let mockSubject: Subject;

  it('should auto generate full name based on first and last name', () => {
    mockSubject = new Subject('John', 'Test', new Date(1, 2, 1950),
      'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));

    expect(mockSubject.fullName).toEqual('John Test');
  });

  it('should accept middle name as optional parameter', () => {
    mockSubject = new Subject('John', 'Test', new Date(1, 2, 1950),
      'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''), 'Adam');

    expect(mockSubject.middleName).toEqual('Adam');
  });

  it('should set middle name to null if not provided', () => {
    mockSubject = new Subject('John', 'Test', new Date(1, 2, 1950),
      'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));

    expect(mockSubject.middleName).toBeNull();
  });

});
