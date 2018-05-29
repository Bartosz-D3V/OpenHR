import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
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
  public workers: Array<LightweightSubject> = [];
  public tableColumns: Array<string> = ['id', 'name', 'position'];
  public dataSource: MatTableDataSource<LightweightSubject> = new MatTableDataSource();

  @ViewChild(MatPaginator) public paginator: MatPaginator;

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
    this.isLoadingResults = true;
    this.$workers = this._workersService.getWorkers().subscribe(
      (result: Array<LightweightSubject>) => {
        this.workers = result;
        this.dataSource.data = result;
        this.dataSource.paginator = this.paginator;
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
