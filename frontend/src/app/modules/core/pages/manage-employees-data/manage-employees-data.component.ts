import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { RegularExpressions } from '../../../../shared/constants/regexps/regular-expressions';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { Subject } from '../../../../shared/domain/subject/subject';
import { ISubscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-manage-employees-data',
  templateUrl: './manage-employees-data.component.html',
  styleUrls: ['./manage-employees-data.component.scss'],
  providers: [
    SubjectDetailsService,
  ],
})
export class ManageEmployeesDataComponent implements OnInit, OnDestroy {
  private $subject: ISubscription;
  subject: Subject;
  employeeForm: FormGroup;

  constructor(private _subjectService: SubjectDetailsService,
              private _fb: FormBuilder) {
  }

  ngOnInit() {
    this.constructForm();
  }

  ngOnDestroy(): void {
    this.$subject.unsubscribe();
  }

  private constructForm(): void {
    this.employeeForm = this._fb.group({
      personalInformation: this._fb.group({
        firstName: [Validators.required],
        lastName: [Validators.required],
        middleName: [],
        dob: [Validators.required],
        position: [],
      }),
      contactInformation: this._fb.group({
        email: [
          Validators.required,
          Validators.pattern(RegularExpressions.EMAIL)],
        telephone: ['',
          Validators.required,
          Validators.pattern(RegularExpressions.NUMBERS_ONLY),
          Validators.minLength(7),
          Validators.maxLength(11)],
        firstLineAddress: [],
        secondLineAddress: [],
        thirdLineAddress: [],
        postcode: ['',
          Validators.required,
          Validators.pattern(RegularExpressions.UK_POSTCODE)],
        city: [],
        country: [],
      }),
      employeeInformation: this._fb.group({
        nin: ['',
          Validators.required,
          Validators.pattern(RegularExpressions.NIN)],
        employeeId: ['', Validators.required],
        startDate: [],
        endDate: [],
      }),
      hrInformation: this._fb.group({
        allowance: ['', Validators.min(0)],
        usedAllowance: ['', Validators.min(0)],
        manager: ['', Validators.required],
      }),
    });
  }

  private fetchSubject(subjectId: number): void {
    this.$subject = this._subjectService
      .getSubjectById(subjectId)
      .subscribe((result: Subject) => this.subject = result);
  }

  save(): void {

  }
}
