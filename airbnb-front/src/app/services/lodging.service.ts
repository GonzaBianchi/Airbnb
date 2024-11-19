// src/app/services/lodging.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Lodging {
  id?: number;
  descripcion: string;
  imagen: string;
  precio_por_noche: number;
  id_ciudad?: number;  // Cambiar 'ciudad' a 'id_ciudad'
  id_tipo_hospedaje?: number;  // Cambiar 'tipoHospedaje' a 'id_tipo_hospedaje'
  servicios: { id: number }[];
}


@Injectable({
  providedIn: 'root'
})
export class LodgingService {
  private apiUrl = `http://localhost:8080/api/hospedajes`;

  constructor(private http: HttpClient) {}

  getMisHospedajes(): Observable<Lodging[]> {
    return this.http.get<Lodging[]>(`${this.apiUrl}/mis-hospedajes`);
  }

  crearHospedaje(lodging: Lodging): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, lodging, { responseType: 'text' });
  }

  modificarHospedaje(id: number, lodging: Lodging): Observable<Lodging> {
    return this.http.put<Lodging>(`${this.apiUrl}/modificar/${id}`, lodging);
  }

  eliminarHospedaje(id: number): Observable<Lodging> {
    return this.http.put<Lodging>(`${this.apiUrl}/eliminar/${id}`, {});
  }
}