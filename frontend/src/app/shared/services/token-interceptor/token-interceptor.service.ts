import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { SystemVariables } from '@config/system-variables';

@Injectable()
export class TokenInterceptorService implements HttpInterceptor {
  private readonly skipURLs: Array<string> = [SystemVariables.API_URL + '/auth/token'];

  constructor(private _jwtHelper: JwtHelperService) {}

  public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.skipURLs.includes(req.url)) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer-${this._jwtHelper.getToken()}`,
        },
      });
    }

    return next.handle(req);
  }
}
