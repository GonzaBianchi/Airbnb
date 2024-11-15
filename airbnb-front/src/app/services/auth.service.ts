import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface TipoUsuario {
  id: number;
  nombre: string;
}

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

export interface AuthResponse {
  token?: string;
  message?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  register(data: RegistrationData): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data, { responseType: 'text' })
      .pipe(catchError(this.handleError));
  }

  login(data: LoginData): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, data)
      .pipe(catchError(this.handleError));
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
}