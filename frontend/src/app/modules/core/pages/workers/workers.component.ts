import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { ISubscription } from 'rxjs/Subscription';
import 'rxjs/add/operator/retry';

import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { WorkersService } from '@modules/core/pages/workers/service/workers.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { SystemVariables } from '@config/system-variables';

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.scss'],
  providers: [WorkersService],
})
export class WorkersComponent implements OnInit, OnDestroy {
  private $workers: ISubscription;
  public isFetching: boolean;
  public workers: Array<LightweightSubject> = [];
  public tableColumns: Array<string> = ['id', 'name', 'position'];
  public dataSource: MatTableDataSource<LightweightSubject> = new MatTableDataSource();

  @ViewChild(MatPaginator)
  public paginator: MatPaginator;

  constructor(private _workersService: WorkersService, private _errorResolver: ErrorResolverService) {}

  public ngOnInit(): void {
    this.fetchWorkers();
  }

  public ngOnDestroy(): void {
    if (this.$workers) {
      this.$workers.unsubscribe();
    }
  }

  public fetchWorkers(): void {
    this.isFetching = true;
    this.$workers = this._workersService
      .getWorkers()
      .retry(SystemVariables.RETRY_TIMES)
      .finally(() => (this.isFetching = false))
      .subscribe(
        (result: Array<LightweightSubject>) => {
          this.workers = result;
          this.dataSource.data = result;
          this.dataSource.paginator = this.paginator;
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
  }

  public applyFilter(filterValue: string): void {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }
}
