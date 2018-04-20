import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';

@Injectable()
export class TokenInterceptorService implements HttpInterceptor {
  constructor(private _jwtHelper: JwtHelperService) {}

  public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer-${this._jwtHelper.getToken()}`,
      },
    });

    return next.handle(req);
  }
}
