// src/app/services/lodging.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LodgingResponse {
  id: number;
  descripcion: string;
  imagen: string;
  precio_por_noche: number;
  ciudad: {
    id: number;
    nombre: string;
    pais: {
      id: number;
      nombre: string;
    }
  };
  id_tipo_hospedaje: {
    id: number;
    nombre: string;
  };
  servicios: {
    id: number;
    nombre: string;
    borrado: boolean;
  }[];
}

export interface LodgingRequest {
  id?: number;
  descripcion: string;
  imagen: string;
  precio_por_noche: number;
  id_ciudad: number;
  id_tipo_hospedaje: number;
  servicios: { id: number }[];
}


@Injectable({
  providedIn: 'root'
})
export class LodgingService {
  private apiUrl = `http://localhost:8080/api/hospedajes`;

  constructor(private http: HttpClient) {}

  getMisHospedajes(): Observable<LodgingResponse[]> {
    return this.http.get<LodgingResponse[]>(`${this.apiUrl}/mis-hospedajes`);
  }

  crearHospedaje(lodging: LodgingRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, lodging, { responseType: 'text' });
  }

  modificarHospedaje(id: number, lodging: LodgingRequest): Observable<any> {
    return this.http.put(`${this.apiUrl}/modificar/${id}`, lodging, { responseType: 'text' });
  }

  eliminarHospedaje(id: number): Observable<LodgingRequest> {
    return this.http.put<LodgingRequest>(`${this.apiUrl}/eliminar/${id}`, {});
  }
}