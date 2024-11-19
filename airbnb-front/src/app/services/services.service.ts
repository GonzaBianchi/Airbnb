import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Services {
  id?: number;
  nombre: string;
  borrado?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ServicesService {
  private apiUrl = 'http://localhost:8080/api/servicios';

  constructor(
    private http: HttpClient
  ) { }

  getServicios(): Observable<Services[]> {
    return this.http.get<Services[]>(this.apiUrl);
  }

  crearServicio(servicio: Services): Observable<Services> {
    return this.http.post<Services>(`${this.apiUrl}/crear`, servicio);
  }

  modificarServicio(id: number, servicio: Services): Observable<Services> {
    return this.http.put<Services>(`${this.apiUrl}/${id}`, servicio);
  }

  borrarServicio(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/borrar/${id}`, {}, { responseType: 'text' });
  }

  getServicioPorNombre(nombre: string): Observable<Services> {
    return this.http.get<Services>(`${this.apiUrl}/${nombre}`);
  }
}