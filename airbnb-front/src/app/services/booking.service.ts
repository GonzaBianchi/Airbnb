import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BookingRequest {
  id_hospedaje: number,
  fecha_check_in: string,
  fecha_check_out: string, 
  cant_ninos: number,
  cant_adultos: number,
  cant_bebes: number,
  cant_mascotas: number
}

export interface BookingResponse {
  id: number,
  hospedaje: {
    id: number,
    nombre: string
  },
  id_usuario: number,
  fecha_check_in: string,
  fecha_check_out: string,
  cant_adultos: number
  cant_bebes: number,
  cant_mascotas: number,
  importe_total: number,
  estado: string,
  cant_ninos: number
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = `http://localhost:8080/api/reservas`;

  constructor(private http: HttpClient) { }

  crearReserva(reserva: BookingRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, reserva, { responseType: 'text'});
  }

  modificarReserva(idReserva: number, reserva: BookingRequest): Observable<any> {
    return this.http.put(`${this.apiUrl}/modificar/${idReserva}`, reserva, { responseType: 'text'});
  }

  getReservasByUser(): Observable<BookingResponse[]> {
    return this.http.get<BookingResponse[]>(`${this.apiUrl}/mis-reservas`);
  }

  getReservasAnfitrion(): Observable<BookingResponse[]> {
    return this.http.get<BookingResponse[]>(`${this.apiUrl}/ver-reservas`);
  }

  confirmarReserva(idReserva: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/confirmar/${idReserva}`, {}, { responseType: 'text'});
  }

  cancelarReserva(idReserva: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/cancelar/${idReserva}`, {}, { responseType: 'text'});
  }
}