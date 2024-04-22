import {Injectable, OnInit} from "@angular/core";
import {
  HttpClient,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {catchError, Observable, of, throwError} from "rxjs";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  private readonly token!: string | null;

  constructor() {
    this.token = localStorage.getItem('token');
  }

  public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.token){
      return next.handle(req);
    }
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${this.token}`)
    });
    return next.handle(authReq).pipe(
      catchError((error: any) => {
        if (error.error.errorCode === "AUTHENTICATION_ERROR") {
          localStorage.clear();
        }
        return of();
      })
    );
  }
}
