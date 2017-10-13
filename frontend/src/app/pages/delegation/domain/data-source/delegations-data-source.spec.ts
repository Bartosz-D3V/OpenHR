import { DelegationsDataSource } from './delegations-data-source';
import { Destination } from '../destination/destination';
import { Delegation } from '../delegation/delegation';

describe('DestinationDataSource', () => {

  let destinationDataSource: DelegationsDataSource;
  const mockDestination: Destination = new Destination('United Kingdom', 'Nottinghamshire');
  const mockDelegation: Delegation = new Delegation(mockDestination, [new Date()], 1000);
  const mockDestinations: Array<Delegation> = [mockDelegation];

  beforeEach(() => {
    destinationDataSource = new DelegationsDataSource(mockDestinations);
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
