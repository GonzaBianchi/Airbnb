import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { LodgingService, LodgingResponse, FiltroHospedaje } from '../../services/lodging.service';
import { CountrycityService, Pais, Ciudad } from '../../services/countrycity.service';
import { ServicesService, Services } from '../../services/services.service';
import { LodgingtypeService, LodgingType } from '../../services/lodgingtype.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lodgings',
  templateUrl: './lodgings.component.html',
  styleUrls: ['./lodgings.component.css']
})
export class LodgingsComponent implements OnInit{
  filterForm: FormGroup;
  paises: Pais[] = [];
  ciudades: Ciudad[] = [];
  tiposHospedaje: LodgingType[] = [];
  servicios: Services[] = [];
  hospedajesFiltered: LodgingResponse[] = [];
  serviciosSeleccionados: number[] = [];
  cargando: boolean = true;

  constructor(
    private fb: FormBuilder,
    private lodgingService: LodgingService,
    private paisCiudadService: CountrycityService,
    private serviciosService: ServicesService,
    private tipoHospedajeService: LodgingtypeService,
    private router: Router
  ) {
    this.filterForm = this.fb.group({
      pais: [''],
      ciudad: [''],
      tipo: ['']
    });
  }

  ngOnInit() {
    this.cargarDatosIniciales();
    this.cargarTodosHospedajes();
  }

  cargarDatosIniciales() {
    this.paisCiudadService.getPaises().subscribe(paises => this.paises = paises);
    this.tipoHospedajeService.getLodgingTypes().subscribe(tipos => this.tiposHospedaje = tipos);
    this.serviciosService.getServicios().subscribe(servicios => this.servicios = servicios);
  }

  cargarTodosHospedajes() {
    this.cargando = true;
    this.lodgingService.getAll().subscribe({
      next: (hospedajes) => (
        this.hospedajesFiltered = hospedajes,
        console.log('Cargando hospedajes', this.hospedajesFiltered),
        this.cargando = false
      ),
      error: (error) => (
        console.error('Error al cargar hospedajes', error),
        this.cargando = false
      )
    });
  }

  onPaisChange() {
    const paisSeleccionado = this.filterForm.get('pais')?.value;
    const paisObj = this.paises.find(p => p.nombre === paisSeleccionado);
    
    if (paisObj) {
      this.paisCiudadService.getCiudades(paisObj.id!).subscribe(
        ciudades => this.ciudades = ciudades
      );
    } else {
      this.ciudades = [];
    }
    // Resetear ciudad al cambiar país
    this.filterForm.get('ciudad')?.setValue('');
  }

  onServicioChange(event: any) {
    const servicioId = event.target.value;
    const isChecked = event.target.checked;

    if (isChecked) {
      this.serviciosSeleccionados.push(Number(servicioId));
    } else {
      const index = this.serviciosSeleccionados.indexOf(Number(servicioId));
      if (index > -1) {
        this.serviciosSeleccionados.splice(index, 1);
      }
    }
  }

  aplicarFiltros() {
    this.cargando = true;
    const filtros: FiltroHospedaje = {
      pais: this.filterForm.get('pais')?.value || undefined,
      ciudad: this.filterForm.get('ciudad')?.value || undefined,
      tipo: this.filterForm.get('tipo')?.value || undefined,
      servicioIds: this.serviciosSeleccionados.length > 0 ? this.serviciosSeleccionados : undefined
    };

    this.lodgingService.getHospedajesFiltered(filtros).subscribe({
      next: (hospedajes) => (
        this.hospedajesFiltered = hospedajes,
        this.cargando = false
      ),
      error: (error) => (
        console.error('Error al filtrar hospedajes', error),
        this.cargando = false
      )
    });
  }

  resetFiltros() {
    // Resetear formulario
    this.filterForm.reset({
      pais: '',
      ciudad: '',
      tipo: ''
    });
    
    // Limpiar servicios seleccionados
    this.serviciosSeleccionados = [];
    
    // Desmarcar checkboxes de servicios
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach((checkbox: any) => checkbox.checked = false);

    // Cargar todos los hospedajes nuevamente
    this.cargarTodosHospedajes();
  }

  getServiciosNombres(servicios: { id: number; nombre: string; borrado: boolean }[]): string {
    return servicios.map(s => s.nombre).join(', ');
  }

  getId(id: number) {
    console.log(id);
    this.router.navigate(['/hospedaje', id]);
  }
}
