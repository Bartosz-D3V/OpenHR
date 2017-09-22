import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RegularExpressions } from '../../shared/constants/regular-expressions';
import { Subject } from '../../shared/classes/subject/subject';
import { SubjectService } from '../../shared/services/subject/subject.service';

@Component({
  selector: 'app-my-details',
  templateUrl: './my-details.component.html',
  styleUrls: ['./my-details.component.scss'],
  providers: [SubjectService],
})
export class MyDetailsComponent implements OnInit {

  public subject: Subject;

  postcodeFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(RegularExpressions.UK_POSTCODE),
  ]);

  emailFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(RegularExpressions.EMAIL),
  ]);

  telephoneFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(7),
    Validators.maxLength(11),
  ]);

  constructor(private subjectService: SubjectService) {
  }

  ngOnInit(): void {
    this.subject = this.subjectService.getCurrentSubject();
  }

  public isValid(): boolean {
    return this.postcodeFormControl.valid &&
      this.emailFormControl.valid &&
      this.telephoneFormControl.valid;
  }

}
