import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Pais {
  id?: number;
  nombre: string;
}

export interface Ciudad {
  id?: number;
  nombre: string;
  pais: number;
}

@Injectable({
  providedIn: 'root'
})
export class CountrycityService {
  private apiUrl = `http://localhost:8080/api`;
  
  constructor(private http: HttpClient) { }

  getPaises(): Observable<Pais[]> {
    return this.http.get<Pais[]>(`${this.apiUrl}/pais/`);  
  }

  getCiudades(pais: number): Observable<Ciudad[]> {
    return this.http.get<Ciudad[]>(`${this.apiUrl}/ciudad/${pais}`);
  }
}
