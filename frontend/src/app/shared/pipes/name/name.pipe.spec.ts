import { NamePipe } from './name.pipe';

describe('NamePipe', () => {
  const pipe: NamePipe = new NamePipe();

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('should capitalize name', () => {
    const mockName1: String = 'test test';
    const mockName2: String = 'Test test test';

    expect(pipe.transform(mockName1)).toEqual('Test Test');
    expect(pipe.transform(mockName2)).toEqual('Test Test Test');
  });
});
