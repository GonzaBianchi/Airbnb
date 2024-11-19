import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LodgingType {
  id?: number;
  nombre: string;
}

@Injectable({
  providedIn: 'root'
})
export class LodgingtypeService {
  private apiUrl = `http://localhost:8080/api/tipoHospedaje`;

  constructor(private http: HttpClient) { }

  getLodgingTypes(): Observable<LodgingType[]> {
    return this.http.get<LodgingType[]>(`${this.apiUrl}/`);
  }
}