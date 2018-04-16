import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ISubscription } from 'rxjs/Subscription';

import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { Subject } from '@shared/domain/subject/subject';
import { RegisterDetails } from '@shared/domain/register/register-details';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss'],
  providers: [SubjectDetailsService],
})
export class AddEmployeeComponent implements OnInit, OnDestroy {
  private $newSubject: ISubscription;
  public newSubjectForm: FormGroup;
  public subject: Subject;
  public registerDetails: RegisterDetails;

  public stepNumber = 0;

  constructor(private _subjectDetailsService: SubjectDetailsService, private _fb: FormBuilder) {}

  ngOnInit(): void {
    this.buildForm();
  }

  ngOnDestroy(): void {}

  public buildForm(): void {
    this.newSubjectForm = this._fb.group({
      personalInformation: this._fb.group({
        firstName: [Validators.required],
        middleName: [],
        lastName: [Validators.required],
        dateOfBirth: [Validators.required],
      }),
      contactInformation: this._fb.group({
        telephone: [
          Validators.compose([
            Validators.required,
            Validators.pattern(RegularExpressions.NUMBERS_ONLY),
            Validators.minLength(7),
            Validators.maxLength(11),
          ]),
        ],
        email: [Validators.compose([Validators.required, Validators.pattern(RegularExpressions.EMAIL)])],
        address: this._fb.group({
          firstLineAddress: [],
          secondLineAddress: [],
          thirdLineAddress: [],
          postcode: [Validators.compose([Validators.required, Validators.pattern(RegularExpressions.UK_POSTCODE)])],
          city: [],
          country: [],
        }),
      }),
      employeeInformation: this._fb.group({
        nationalInsuranceNumber: [Validators.compose([Validators.required, Validators.pattern(RegularExpressions.NIN)])],
        position: [],
        department: [],
        employeeNumber: [employeeInfo.employeeNumber, Validators.required],
        startDate: [employeeInfo.startDate],
        endDate: [employeeInfo.endDate],
      }),
      hrInformation: this._fb.group({
        allowance: [Validators.min(0)],
        usedAllowance: [Validators.min(0)],
      }),
      role: [],
      user: this._fb.group({
        password: [Validators.required],
        username: [Validators.required],
      }),
    });
  }

  public setStep(stepNumber: number): void {
    this.stepNumber = stepNumber;
  }

  public getStep(): number {
    return this.stepNumber;
  }

  public nextStep(): void {
    this.stepNumber++;
  }

  public prevStep(): void {
    this.stepNumber--;
  }

  public isValid(): boolean {
    return this.newSubjectForm.valid;
  }

  public arePasswordsIdentical(password1: string, password2: string): boolean {
    if (password1 !== password2) {
      this.newSubjectForm.get(['user', 'password']).setErrors({ passwordDoNotMatch: true });
      return false;
    }
    return true;
  }

  public submitForm(subject: Subject, selfAssign: boolean): void {
    if (this.isValid()) {
      this.createSubject(subject);
      if (selfAssign) {
        this.assignEmployeeToManager();
      }
    }
  }

  public createSubject(subject: Subject): void {
    this.$newSubject = this._subjectDetailsService.createSubject(subject).subscribe();
  }

  public assignEmployeeToManager(): void {
    /**
     * call a service
     */
  }
}
