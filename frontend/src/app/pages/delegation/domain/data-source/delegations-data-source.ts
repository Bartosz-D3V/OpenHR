import { CollectionViewer, DataSource } from '@angular/cdk/collections';

import { Observable } from 'rxjs/Observable';
import { Delegation } from '../delegation/delegation';

export class DelegationsDataSource extends DataSource<any> {

  public dataSource: Array<Delegation>;

  constructor(dataSource: Array<Delegation>) {
    super();
    this.dataSource = dataSource;
  }

  connect(collectionViewer: CollectionViewer): Observable<Array<Delegation>> {
    return Observable.of(this.dataSource);
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
