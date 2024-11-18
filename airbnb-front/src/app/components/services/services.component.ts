import { Component, OnInit } from '@angular/core';
import { ServicesService, Services } from '../../services/services.service';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css'],
  standalone: false
})
export class ServicesComponent implements OnInit {
  servicios: Services[] = [];
  nuevoServicio: Services = { nombre: '' };
  servicioSeleccionado: Services | null = null;
  mostrarFormulario = false;
  esEdicion = false;

  constructor(private serviciosService: ServicesService) {}

  ngOnInit(): void {
    this.cargarServicios();
  }

  cargarServicios(): void {
    this.serviciosService.getServicios().subscribe({
      next: (data) => {
        this.servicios = data;
      },
      error: (error) => {
        console.error('Error al cargar servicios', error);
      }
    });
  }

  abrirFormularioCrear(): void {
    this.nuevoServicio = { nombre: '' };
    this.esEdicion = false;
    this.mostrarFormulario = true;
  }

  abrirFormularioEditar(servicio: Services): void {
    this.nuevoServicio = { ...servicio };
    this.esEdicion = true;
    this.mostrarFormulario = true;
  }

  guardarServicio(): void {
    if (this.esEdicion && this.nuevoServicio.id) {
      this.serviciosService.modificarServicio(this.nuevoServicio.id, this.nuevoServicio).subscribe({
        next: () => {
          this.cargarServicios();
          this.mostrarFormulario = false;
        },
        error: (error) => {
          console.error('Error al modificar servicio', error);
          this.mostrarMensajeError(error.error);
        }
      });
    } else {
      this.serviciosService.crearServicio(this.nuevoServicio).subscribe({
        next: () => {
          this.cargarServicios();
          this.mostrarFormulario = false;
        },
        error: (error) => {
          console.error('Error al crear servicio', error);
          this.mostrarMensajeError(error.error);
        }
      });
    }
  }

  borrarServicio(id: number): void {
    if (confirm('¿Está seguro que desea eliminar este servicio?')) {
      this.serviciosService.borrarServicio(id).subscribe({
        next: (response) => {
          alert(response);
          this.cargarServicios();
        },
        error: (error) => {
          console.error('Error al eliminar servicio', error);
        }
      });
    }
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.nuevoServicio = { nombre: '' };
  }

  mostrarMensajeError(mensaje: string): void {
    alert(mensaje);
  }
}