import { CollectionViewer, DataSource } from '@angular/cdk/collections';

import { Observable } from 'rxjs/Observable';

import { Destination } from './destination';

export class DestinationsDataSource extends DataSource<Destination> {

  public dataSource: Array<Destination>;

  constructor(dataSource: Array<Destination>) {
    super();
    this.dataSource = dataSource;
  }

  connect(collectionViewer: CollectionViewer): Observable<Destination[]> {
    return Observable.of(this.dataSource);
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
