import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ISubscription } from 'rxjs/Subscription';
import 'rxjs/add/operator/retry';

import { SystemVariables } from '@config/system-variables';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { TokenObserverService } from '@shared/services/token-observer/token-observer.service';
import { LightweightSubjectService } from './service/lightweight-subject.service';

@Component({
  selector: 'app-core-wrapper',
  templateUrl: './core-wrapper.component.html',
  changeDetection: ChangeDetectionStrategy.Default,
  providers: [LightweightSubjectService, JwtHelperService, TokenObserverService],
})
export class CoreWrapperComponent implements OnInit, OnDestroy {
  private $user: ISubscription;
  public user: LightweightSubject;

  constructor(
    private _lightweightSubject: LightweightSubjectService,
    private _jwtHelper: JwtHelperService,
    private _tokenObserver: TokenObserverService,
    private _router: Router,
    private _activatedRoute: ActivatedRoute
  ) {}

  public ngOnInit(): void {
    this.$user = this._lightweightSubject
      .getUser(this._jwtHelper.getSubjectId())
      .retry(SystemVariables.RETRY_TIMES)
      .subscribe((value: LightweightSubject) => {
        this.user = new LightweightSubject(value.subjectId, value.firstName, value.lastName, value.position);
        this._router.navigate([{ outlets: { core: ['dashboard'] } }], { relativeTo: this._activatedRoute });
      });
    this._tokenObserver.observe();
  }

  public ngOnDestroy(): void {
    this.$user.unsubscribe();
  }
}
