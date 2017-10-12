import { DestinationsDataSource } from './destinations-data-source';
import { Destination } from '../destination/destination';

describe('DestinationDataSource', () => {

  let destinationDataSource: DestinationsDataSource;
  const mockDestinations: Array<Destination> = [new Destination('United Kingdom', 'London')];

  beforeEach(() => {
    destinationDataSource = new DestinationsDataSource(mockDestinations);
  });

  it('should be created', () => {
    expect(destinationDataSource).toBeTruthy();
  });

  it('connect method should return an observable', () => {
    let result;
    destinationDataSource.connect(null).subscribe((data) => {
      result = data;
    });

    expect(result).toBeDefined();
    expect(typeof result).toEqual('object');
    expect(result).toEqual(mockDestinations);
  });

});
