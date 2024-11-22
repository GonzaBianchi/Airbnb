import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LodgingResponse {
  id: number;
  nombre: string;
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
  nombre: string;
  descripcion: string;
  imagen: string;
  precio_por_noche: number;
  id_ciudad: number;
  id_tipo_hospedaje: number;
  servicios: { id: number }[];
}

export interface FiltroHospedaje {
  pais?: string;
  ciudad?: string;
  tipo?: string;
  servicioIds?: number[];
}


@Injectable({
  providedIn: 'root'
})
export class LodgingService {
  private apiUrl = `http://localhost:8080/api/hospedajes`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<LodgingResponse[]> {
    return this.http.get<LodgingResponse[]>(`${this.apiUrl}/`);
  }

  getHospedajesFiltered(filtros: FiltroHospedaje): Observable<LodgingResponse[]> {
    let params = new HttpParams();

    if (filtros.pais) {
      params = params.append('pais', filtros.pais);
    }
    if (filtros.ciudad) {
      params = params.append('ciudad', filtros.ciudad);
    }
    if (filtros.tipo) {
      params = params.append('tipo', filtros.tipo);
    }
    if (filtros.servicioIds && filtros.servicioIds.length > 0) {
      filtros.servicioIds.forEach(id => {
        params = params.append('servicioIds', id.toString());
      });
    }

    return this.http.get<LodgingResponse[]>(`${this.apiUrl}/filtrar`, { params });
  }

  getHospedajeUnico(id: number): Observable<LodgingResponse> {
    return this.http.get<LodgingResponse>(`${this.apiUrl}/ver/${id}`);
  }

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