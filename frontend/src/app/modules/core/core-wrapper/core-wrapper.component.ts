import { Component, OnDestroy, OnInit } from '@angular/core';

import { ISubscription } from 'rxjs/Subscription';

import { User } from '../../../shared/domain/user/user';
import { JwtHelperService } from '../../../shared/services/jwt/jwt-helper.service';
import { LightweightSubjectService } from './service/lightweight-subject.service';

@Component({
  selector: 'app-core-wrapper',
  templateUrl: './core-wrapper.component.html',
  styleUrls: ['./core-wrapper.component.scss'],
  providers: [
    LightweightSubjectService,
    JwtHelperService,
  ],
})
export class CoreWrapperComponent implements OnInit, OnDestroy {
  public user: User;
  private $user: ISubscription;

  constructor(private _lightweightSubject: LightweightSubjectService,
              private _jwtHelper: JwtHelperService) {
  }

  ngOnInit(): void {
    this.$user = this._lightweightSubject
      .getUser(this._jwtHelper.getSubjectId())
      .subscribe((value: User) => {
        this.user = new User(value.subjectId, value.firstName, value.lastName);
      });
  }

  ngOnDestroy(): void {
    this.$user.unsubscribe();
  }
}
