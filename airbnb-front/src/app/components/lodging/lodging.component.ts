import { Component, OnInit } from '@angular/core';
import { Lodging, LodgingService } from 'src/app/services/lodging.service';
import { LodgingType, LodgingtypeService } from 'src/app/services/lodgingtype.service';
import { Ciudad, CountrycityService, Pais } from 'src/app/services/countrycity.service';
import { Services, ServicesService } from 'src/app/services/services.service';

@Component({
  selector: 'app-lodging',
  templateUrl: './lodging.component.html',
  styleUrls: ['./lodging.component.css']
})
export class LodgingComponent implements OnInit {
  misHospedajes: Lodging[] = [];
  tiposHospedaje: LodgingType[] = [];
  paises: Pais[] = [];
  ciudades: Ciudad[] = [];
  servicios: Services[] = [];
  selectedPais: number | null = null; // ID del país seleccionado
  mostrarFormulario = false;
  editando = false;
  lodgingForm = {
    descripcion: '',
    imagen: '',
    precioPorNoche: 0,
    id_ciudad: 0,
    id_tipo_hospedaje: 0
  };
  selectedServicios: number[] = [];
  idHospedajeEditar: number | null = null;

  constructor(
    private lodgingService: LodgingService,
    private lodgingtypeService: LodgingtypeService,
    private countrycityService: CountrycityService,
    private servicesService: ServicesService
  ) {}

  ngOnInit(): void {
    this.cargarMisHospedajes();
    this.cargarTiposHospedaje();
    this.cargarPaises();
    this.cargarServicios();
  }

  cargarMisHospedajes(): void {
    this.lodgingService.getMisHospedajes().subscribe({
      next: (data) => (this.misHospedajes = data),
      error: (error) => console.error('Error al cargar mis hospedajes', error)
    });
  }

  cargarTiposHospedaje(): void {
    this.lodgingtypeService.getLodgingTypes().subscribe({
      next: (data) => (this.tiposHospedaje = data),
      error: (error) => console.error('Error al cargar tipos de hospedaje', error)
    });
  }

  cargarPaises(): void {
    this.countrycityService.getPaises().subscribe({
      next: (data) => (this.paises = data),
      error: (error) => console.error('Error al cargar países', error)
    });
  }

  cargarCiudades(paisId: number): void {
    this.selectedPais = paisId;
    this.countrycityService.getCiudades(paisId).subscribe({
      next: (data) => (this.ciudades = data),
      error: (error) => console.error('Error al cargar ciudades', error)
    });
  }

  cargarServicios(): void {
    this.servicesService.getServicios().subscribe({
      next: (data) => (this.servicios = data),
      error: (error) => console.error('Error al cargar servicios', error)
    });
  }

  crearHospedaje(): void {
    const nuevoHospedaje: Lodging = {
      descripcion: this.lodgingForm.descripcion,
      imagen: this.lodgingForm.imagen,
      precioPorNoche: this.lodgingForm.precioPorNoche,
      ciudad: this.lodgingForm.id_ciudad, // Ajusta para usar 'ciudad'
      tipoHospedaje: this.lodgingForm.id_tipo_hospedaje, // Ajusta para usar 'tipoHospedaje'
      servicios: this.selectedServicios.map((id) => ({ id }))
    };

    this.lodgingService.crearHospedaje(nuevoHospedaje).subscribe({
      next: () => {
        alert('Hospedaje creado con éxito');
        this.cargarMisHospedajes(); // Refrescar lista de hospedajes
      },
      error: (error) => console.error('Error al crear hospedaje', error)
    });
  }

  editarHospedaje(hospedaje: Lodging): void {
    this.editando = true;
    this.mostrarFormulario = true;
    this.idHospedajeEditar = hospedaje.id!;
    this.lodgingForm = {
      descripcion: hospedaje.descripcion,
      imagen: hospedaje.imagen,
      precioPorNoche: hospedaje.precioPorNoche,
      id_ciudad: hospedaje.ciudad,
      id_tipo_hospedaje: hospedaje.tipoHospedaje
    };
    this.selectedServicios = hospedaje.servicios.map((s: any) => s.id);
  }
  
  guardarHospedaje(): void {
    const nuevoHospedaje: Lodging = {
      descripcion: this.lodgingForm.descripcion,
      imagen: this.lodgingForm.imagen,
      precioPorNoche: this.lodgingForm.precioPorNoche,
      ciudad: this.lodgingForm.id_ciudad, // Ajusta para usar 'ciudad'
      tipoHospedaje: this.lodgingForm.id_tipo_hospedaje, // Ajusta para usar 'tipoHospedaje'
      servicios: this.selectedServicios.map((id) => ({ id }))
    };
  
    if (this.editando && this.idHospedajeEditar !== null) {
      this.lodgingService.modificarHospedaje(this.idHospedajeEditar, nuevoHospedaje).subscribe({
        next: () => {
          alert('Hospedaje editado con éxito');
          this.cargarMisHospedajes();
          this.cancelarEdicion();
        },
        error: (error) => console.error('Error al editar hospedaje', error)
      });
    } else {
      this.lodgingService.crearHospedaje(nuevoHospedaje).subscribe({
        next: () => {
          alert('Hospedaje creado con éxito');
          this.cargarMisHospedajes();
          this.cancelarEdicion();
        },
        error: (error) => console.error('Error al crear hospedaje', error)
      });
    }
  }
  
  eliminarHospedaje(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este hospedaje?')) {
      this.lodgingService.eliminarHospedaje(id).subscribe({
        next: () => {
          alert('Hospedaje eliminado con éxito');
          this.cargarMisHospedajes();
        },
        error: (error) => console.error('Error al eliminar hospedaje', error)
      });
    }
  }
  
  cancelarEdicion(): void {
    this.mostrarFormulario = false;
    this.editando = false;
    this.lodgingForm = {
      descripcion: '',
      imagen: '',
      precioPorNoche: 0,
      id_ciudad: 0,
      id_tipo_hospedaje: 0
    };
    this.selectedServicios = [];
    this.idHospedajeEditar = null;
  }
}
