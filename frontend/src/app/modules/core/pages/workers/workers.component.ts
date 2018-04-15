import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { ISubscription } from 'rxjs/Subscription';

import { WorkerDataObject } from './domain/employee-data-object';
import { Worker } from '@shared/domain/subject/employee';
import { WorkerData } from './employee-data';

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.scss'],
})
export class WorkersComponent implements OnInit, OnDestroy, AfterViewInit {
  private $workers: ISubscription;
  public workers: Array<WorkerData>;
  public tableColumns: Array<string> = ['id', 'name', 'position'];
  public dataSource: MatTableDataSource<WorkerData>;

  @ViewChild(MatPaginator) public paginator: MatPaginator;

  @ViewChild(MatSort) public sort: MatSort;

  constructor(private _workersService: WorkersService) {
    this.dataSource = new MatTableDataSource(this.workers);
  }

  ngOnInit(): void {
    this.fetchSubjects();
  }

  ngOnDestroy(): void {
    this.$workers.unsubscribe();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  public fetchSubjects(): void {
    this.$workers = this._workersService.getWorkers().subscribe((result: Array<Worker>) => {
      this.workers = this.simplifyWorkerArray(result);
    });
  }

  public applyFilter(filterValue: string): void {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  public simplifyWorkerArray(result: Array<Worker>): Array<WorkerData> {
    const employeeDataArray: Array<WorkerData> = [];
    for (let i = 0; i < result.length; ++i) {
      employeeDataArray.push(this.simplifyWorkerObject(result[i]));
    }
    return employeeDataArray;
  }

  public simplifyWorkerObject(employee: Worker): WorkerData {
    const fullName = `${employee.personalInformation.firstName} ${employee.personalInformation.lastName}`;
    return new WorkerDataObject(employee.subjectId, fullName, employee.employeeInformation.position);
  }
}
