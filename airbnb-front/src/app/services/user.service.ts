import { Injectable } from '@angular/core';
import { AuthService, TipoUsuario } from './auth.service';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';

export interface Usuario {
  id: number;
  username: string;
  password: string;
  nombre: string;
  apellido: string;
  email: string;
  dni: string;
  fecha_nacimiento: string;
  tipoUsuarios: TipoUsuario[];
}

export interface EditarUsuarioDTO {
  username: string;
  nombre: string;
  apellido: string;
  email: string;
  dni: string;
  password: string;
  fecha_nacimiento: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/usuarios/';
  
  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}

  obtenerUsuario(): Observable<Usuario> {
    const decodedToken = this.authService.getDecodedToken();
    
    if (!decodedToken?.sub) {
      return throwError(() => new Error('No se encontró el token de usuario'));
    }

    return this.http.get<Usuario>(`${this.apiUrl}${decodedToken.sub}`).pipe(
      catchError(this.handleError)
    );
  }

  modificarUsuario(datosUsuario: EditarUsuarioDTO): Observable<any> {
    const username = this.authService.getDecodedToken()?.sub;
    
    if (!username) {
      return throwError(() => new Error('No se encontró el token de usuario'));
    }

    return this.http.put(`${this.apiUrl}modificar`, datosUsuario, { responseType: 'text' })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Error al modificar el usuario';
          console.error('Error detallado:', error);
          return throwError(() => errorMessage);
        })
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Ocurrió un error desconocido';
    
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      switch (error.status) {
        case 404:
          errorMessage = 'Usuario no encontrado';
          break;
        case 401:
          errorMessage = 'No autorizado';
          break;
        case 403:
          errorMessage = 'Acceso denegado';
          break;
        default:
          errorMessage = `Error del servidor: ${error.status}`;
      }
    }
    
    return throwError(() => new Error(errorMessage));
  }
}