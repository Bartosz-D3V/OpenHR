import { CollectionViewer, DataSource } from '@angular/cdk/collections';

import { Observable } from 'rxjs/Observable';

export class DestinationsDataSource extends DataSource<string> {

  public dataSource: Array<string>;

  constructor(dataSource: Array<string>) {
    super();
    this.dataSource = dataSource;
  }

  connect(collectionViewer: CollectionViewer): Observable<string[]> {
    return Observable.of(this.dataSource);
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
