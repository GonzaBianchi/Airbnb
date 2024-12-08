import { Injectable } from '@angular/core';
import { 
  CanActivate, 
  RouterStateSnapshot, 
  UrlTree, 
  Router
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(
    _: unknown,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.isLoggedIn()&& !this.authService.getTipoUsuarios().some(tipo => tipo.nombre === 'ADMINISTRADOR')) {
      return true;
    }

    return this.router.createUrlTree(['/login'], { queryParams: { returnUrl: state.url } });
  }
}
