import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

export interface BookingRequest {
  id_hospedaje: number;
  fecha_check_in: string;
  fecha_check_out: string;
  cant_ninos: number;
  cant_adultos: number;
  cant_bebes: number;
  cant_mascotas: number;
}

export interface BookingResponse {
  id: number;
  hospedaje: {
    id: number;
    nombre: string;
  };
  id_usuario: number;
  fecha_check_in: string;
  fecha_check_out: string;
  cant_adultos: number;
  cant_bebes: number;
  cant_mascotas: number;
  importe_total: number;
  estado: string;
  cant_ninos: number;
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = 'http://localhost:8080/api/reservas';

  constructor(private http: HttpClient) { }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Ha ocurrido un error';
    
    if (error.error instanceof ErrorEvent) {
      // Error del cliente
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Error del servidor
      if (error.status === 404) {
        errorMessage = 'No se encontró el recurso solicitado';
      } else if (error.status === 400) {
        errorMessage = 'Solicitud incorrecta';
      } else if (error.status === 403) {
        errorMessage = 'No tiene permisos para realizar esta acción';
      } else if (error.status === 500) {
        errorMessage = 'Error interno del servidor';
      }
    }
    
    console.error('Error en la solicitud:', error);
    return throwError(() => new Error(errorMessage));
  }

  crearReserva(reserva: BookingRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/crear`, reserva, { responseType: 'text'})
      .pipe(
        catchError(this.handleError)
      );
  }

  modificarReserva(idReserva: number, reserva: BookingRequest): Observable<string> {
    return this.http.put(`${this.apiUrl}/modificar/${idReserva}`, reserva, { responseType: 'text'})
      .pipe(
        catchError(this.handleError)
      );
  }

  getReservasByUser(): Observable<BookingResponse[]> {
    return this.http.get<BookingResponse[]>(`${this.apiUrl}/mis-reservas`)
      .pipe(
        map(response => response || []),
        catchError(error => {
          if (error.status === 500 && error.error?.includes('no tiene reservas')) {
            return of([]); // Retorna array vacío si no hay reservas
          }
          return this.handleError(error);
        })
      );
  }

  getReservasAnfitrion(): Observable<BookingResponse[]> {
    return this.http.get<BookingResponse[]>(`${this.apiUrl}/ver-reservas`)
      .pipe(
        map(response => response || []),
        catchError(error => {
          if (error.status === 500 && error.error?.includes('no tiene reservas')) {
            return of([]); // Retorna array vacío si no hay reservas
          }
          return this.handleError(error);
        })
      );
  }

  confirmarReserva(idReserva: number): Observable<string> {
    return this.http.put(`${this.apiUrl}/confirmar/${idReserva}`, {}, { responseType: 'text'})
      .pipe(
        catchError(this.handleError)
      );
  }

  cancelarReserva(idReserva: number): Observable<string> {
    return this.http.put(`${this.apiUrl}/cancelar/${idReserva}`, {}, { responseType: 'text'})
      .pipe(
        catchError(this.handleError)
      );
  }
}