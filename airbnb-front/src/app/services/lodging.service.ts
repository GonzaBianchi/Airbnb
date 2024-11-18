import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Hospedajes {
  id?: number;
  nombre: string;
  borrado?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class LodgingService {
  private apiUrl = 'http://localhost:8080/api/hospedajes';

  constructor(
    private http: HttpClient
  ) { }

  getHospedajes(): Observable<Hospedajes[]> {
    return this.http.get<Hospedajes[]>(this.apiUrl);
  }

  crearHospedaje(hospedaje: Hospedajes): Observable<Hospedajes> {
    return this.http.post<Hospedajes>(`${this.apiUrl}/crear`, hospedaje);
  }

  modificarHospedaje(id: number, hospedaje: Hospedajes): Observable<Hospedajes> {
    return this.http.put<Hospedajes>(`${this.apiUrl}/${id}`, hospedaje);
  }

  borrarHospedaje(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/borrar/${id}`, {}, { responseType: 'text' });
  }

  gethospedajePorNombre(nombre: string): Observable<Hospedajes> {
    return this.http.get<Hospedajes>(`${this.apiUrl}${nombre}`);
  }
}