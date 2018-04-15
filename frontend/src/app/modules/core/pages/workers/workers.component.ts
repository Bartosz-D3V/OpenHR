import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { ISubscription } from 'rxjs/Subscription';

import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { WorkersService } from '@modules/core/pages/workers/service/workers.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.scss'],
  providers: [WorkersService],
})
export class WorkersComponent implements OnInit, OnDestroy {
  private $workers: ISubscription;
  public isLoadingResults: boolean;
  public workers: Array<LightweightSubject>;
  public tableColumns: Array<string> = ['id', 'name', 'position'];
  public dataSource: MatTableDataSource<LightweightSubject>;

  @ViewChild(MatPaginator) public paginator: MatPaginator;

  @ViewChild(MatSort) public sort: MatSort;

  constructor(private _workersService: WorkersService, private _errorResolver: ErrorResolverService) {}

  ngOnInit(): void {
    this.fetchWorkers();
  }

  ngOnDestroy(): void {
    if (this.$workers !== undefined) {
      this.$workers.unsubscribe();
    }
  }

  public fetchWorkers(): void {
    this.isLoadingResults = true;
    this.$workers = this._workersService.getWorkers().subscribe(
      (result: Array<LightweightSubject>) => {
        this.workers = result;
        this.dataSource = new MatTableDataSource(this.workers);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.isLoadingResults = false;
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
