import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private authApiUrl = 'http://localhost:8080/api/auth';

  constructor(private authService: AuthService, private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (request.url.includes(`${this.authApiUrl}/login`) || request.url.includes(`${this.authApiUrl}/register`)) {
      return next.handle(request);
    }

    const token = this.authService.getToken();

    if (token) {
      // Verifica si el token ha expirado
      const isTokenExpired = this.authService.isTokenExpired();
      if (isTokenExpired) {
        this.authService.logout();
        this.router.navigate(['/login'], {
          queryParams: { sessionExpired: true },
        });
        return throwError(() => new Error('Sesión expirada, por favor inicie sesión nuevamente.'));
      }

      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.authService.logout();
          this.router.navigate(['/login'], {
            queryParams: { sessionExpired: true },
          });
        }
        return throwError(() => error);
      })
    );
  }
}
