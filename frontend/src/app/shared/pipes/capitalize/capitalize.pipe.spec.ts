import { CapitalizePipe } from './capitalize.pipe';

describe('CapitalizePipe', () => {
  let pipe: CapitalizePipe;

  beforeEach(() => {
    pipe = new CapitalizePipe();
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('should capitalize first letter', () => {
    const sampleText1 = 'sample text';
    const sampleText2 = 'Sample text';

    expect(pipe.transform(sampleText1)).toBe('Sample text');
    expect(pipe.transform(sampleText2)).toBe('Sample text');
  });
});
