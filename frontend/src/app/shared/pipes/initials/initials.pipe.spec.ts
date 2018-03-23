import { InitialsPipe } from './initials.pipe';

describe('InitialsPipe', () => {
  let pipe: InitialsPipe;

  beforeEach(() => {
    pipe = new InitialsPipe();
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  describe('transform method', () => {
    it('should transform full name into initials', () => {
      const mockName1 = 'John Xavier Black';
      const mockName2 = 'John Test';
      const mockName3 = 'Adam Jan Kowalski';

      expect(pipe.transform(mockName1)).toEqual('JB');
      expect(pipe.transform(mockName2)).toEqual('JT');
      expect(pipe.transform(mockName3)).toEqual('AK');
    });

    it('should transform full name into intials even if followed by a blank spaces', () => {
      const mockName1 = ' Mikolaj Kopernik ';

      expect(pipe.transform(mockName1)).toEqual('MK');
    });

    it('should capitalize the initials', () => {
      const mockName1 = 'jenna smith';

      expect(pipe.transform(mockName1)).toEqual('JS');
    });

    it('should return undefined if no value was provided', () => {
      expect(pipe.transform(null)).toBeUndefined();
    });
  });
});
