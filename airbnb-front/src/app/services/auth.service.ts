import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';

export interface RegistrationData {
  username: string;
  password: string;
  nombre: string;
  apellido: string;
  email: string;
  dni: string;
  fecha_nacimiento: string;
  tipoUsuarios: TipoUsuario[];
}

export interface LoginData {
  username: string;
  password: string;
}
export interface TipoUsuario {
  id: number;
  nombre: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  tipoUsuarios: TipoUsuario[];
  message?: string;
}

interface DecodedToken {
  sub: string;
  tipoUsuarios: TipoUsuario[];
  exp: number;
  iat: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private tokenKey = 'token';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();
  private isInquilinoOrAnfritionSubject = new BehaviorSubject<boolean>(false);
  isInquilinoOrAnfrition$ = this.isInquilinoOrAnfritionSubject.asObservable();
  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.checkToken();
  }

  login(data: LoginData): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, data)
      .pipe(
        tap(response => {
          if (response.token) {
            this.setToken(response.token);
            const decodedToken = this.getDecodedToken();
          }
        }),
        catchError(this.handleError)
      );
  }

  getDecodedToken(): DecodedToken | null {
    const token = this.getToken();
    if (token) {
      try {
        return jwtDecode<DecodedToken>(token);
      } catch {
        return null;
      }
    }
    return null;
  }

  getTipoUsuarios(): TipoUsuario[] {
    const decodedToken = this.getDecodedToken();
    return decodedToken?.tipoUsuarios || [];
  }

  
  
    register(data: RegistrationData): Observable<any> {
      return this.http.post(`${this.apiUrl}/register`, data, { responseType: 'text' })
        .pipe(catchError(this.handleError));
    }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.isAuthenticatedSubject.next(false);
    this.isInquilinoOrAnfritionSubject.next(false);
    this.router.navigate(['/login']);
  }

  private setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.getTipoUsuarios().some(tipo => tipo.nombre === 'ADMINISTRADOR') ? this.isInquilinoOrAnfritionSubject.next(false) : this.isInquilinoOrAnfritionSubject.next(true);
    this.isAuthenticatedSubject.next(true);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) {
      return false;
    }

    try {
      const decodedToken: any = jwtDecode(token);
      const isTokenExpired = decodedToken.exp * 1000 < Date.now();
      
      if (isTokenExpired) {
        this.logout();
        return false;
      }
      
      return true;
    } catch {
      this.logout();
      return false;
    }
  }
  
  public checkToken(): void {
    const isLoggedIn = this.isLoggedIn();
    this.isAuthenticatedSubject.next(isLoggedIn);
  
    if (isLoggedIn) {
      // Verifica si el usuario tiene los roles correctos
      const roles = this.getTipoUsuarios();
      const hasRequiredRole = roles.some(
        tipo => tipo.nombre === 'INQUILINO' || tipo.nombre === 'ANFITRION'
      );
      this.isInquilinoOrAnfritionSubject.next(hasRequiredRole);
    } else {
      this.isInquilinoOrAnfritionSubject.next(false);
    }
  }
  


  private handleError(error: HttpErrorResponse) {
    let errorMessage: string;
    
    if (error.status === 0) {
      errorMessage = 'No se pudo conectar con el servidor';
    } else if (error.status === 400) {
      errorMessage = error.error?.message || 'Datos inválidos';
    } else if (error.status === 401) {
      errorMessage = 'Usuario o contraseña incorrectos';
    } else if (error.status === 409) {
      errorMessage = error.error || 'El usuario ya existe';
    } else {
      errorMessage = error.error || 'Error en el servidor';
    }
    
    return throwError(() => ({ status: error.status, message: errorMessage }));
  }
  isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;
  
    try {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.exp * 1000 < Date.now(); // Verifica si la expiración ya pasó
    } catch {
      return true; // Si no se puede decodificar el token, lo consideramos expirado
    }
  }
}