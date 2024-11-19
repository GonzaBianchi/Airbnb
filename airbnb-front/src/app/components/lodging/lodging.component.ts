import { Component, OnInit } from '@angular/core';
import { Lodging, LodgingService } from 'src/app/services/lodging.service';
import { CountrycityService, Ciudad, Pais } from 'src/app/services/countrycity.service';
import { LodgingtypeService, LodgingType } from 'src/app/services/lodgingtype.service';
import { ServicesService, Services } from 'src/app/services/services.service';

@Component({
  selector: 'app-lodging',
  templateUrl: './lodging.component.html',
  styleUrls: ['./lodging.component.css'],
})
export class LodgingComponent implements OnInit {
  misHospedajes: Lodging[] = [];
  mostrarFormularioCrear = false;
  mostrarFormularioEditar = false;
  paises: Pais[] = [];
  ciudadesDelPais: Ciudad[] = [];
  tiposHospedaje: LodgingType[] = [];
  servicios: Services[] = [];
  
  nuevoHospedaje: Lodging = {
    descripcion: '',
    imagen: '',
    precio_por_noche: 0,
    id_ciudad: 0,
    id_tipo_hospedaje: 0,
    servicios: [],
  };
  
  hospedajeAEditar: Lodging | null = null;

  constructor(
    private lodgingService: LodgingService,
    private ciudadService: CountrycityService,
    private tipoHospedajeService: LodgingtypeService,
    private serviciosService: ServicesService
  ) {}

  ngOnInit(): void {
    this.cargarMisHospedajes();
    this.cargarPaises();
    this.cargarTiposHospedaje();
    this.cargarServicios();
  }

  cargarMisHospedajes(): void {
    this.lodgingService.getMisHospedajes().subscribe({
      next: (data) => (console.log('Mis hospedajes cargados:', data), this.misHospedajes = data),
      error: (error) => console.error('Error al cargar mis hospedajes', error),
    });
  }

  cargarPaises(): void {
    this.ciudadService.getPaises().subscribe({
      next: (data) => (this.paises = data),
      error: (error) => console.error('Error al cargar países', error)
    });
  }

  cargarCiudades(paisId: number): void {
    this.ciudadService.getCiudades(paisId).subscribe({
      next: (data) => (console.log('Ciudades cargadas:', data), this.ciudadesDelPais = data),
      error: (error) => console.error('Error al cargar ciudades', error)
    });
  }

  paisSeleccionado(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const paisId = Number(selectElement.value);
    this.cargarCiudades(paisId);
    this.nuevoHospedaje.id_ciudad = 0;
  }

  cargarTiposHospedaje(): void {
    this.tipoHospedajeService.getLodgingTypes().subscribe({
      next: (data) => (this.tiposHospedaje = data),
      error: (error) => console.error('Error al cargar tipos de hospedaje', error),
    });
  }

  cargarServicios(): void {
    this.serviciosService.getServicios().subscribe({
      next: (data) => (this.servicios = data),
      error: (error) => console.error('Error al cargar servicios', error),
    });
  }

  actualizarServicios(event: any, servicioId?: number): void {
    const checked = event.target.checked;
    
    // Only proceed if servicioId is defined
    if (servicioId !== undefined) {
      if (checked) {
        // Check if service is not already in the list
        if (!this.nuevoHospedaje.servicios.some(s => s.id === servicioId)) {
          this.nuevoHospedaje.servicios.push({ id: servicioId });
        }
      } else {
        // Remove service if unchecked
        this.nuevoHospedaje.servicios = this.nuevoHospedaje.servicios.filter(
          (servicio) => servicio.id !== servicioId
        );
      }
    }
  }

  abrirFormularioCrear(): void {
    console.log('Abrir formulario: antes de cambiar', this.mostrarFormularioCrear);
    this.mostrarFormularioCrear = true;
    console.log('Abrir formulario: después de cambiar', this.mostrarFormularioCrear);
  }
  
  cancelarCreacion(): void {
    console.log('Cancelar creación: antes de cambiar', this.mostrarFormularioCrear);
    this.mostrarFormularioCrear = false;
    console.log('Cancelar creación: después de cambiar', this.mostrarFormularioCrear);
  }
  
  crearHospedaje(): void {
    // Validaciones antes de crear
    if (!this.validarDatosHospedaje()) {
      return;
    }
  
    // Preparar el objeto para enviar
    const hospedajeParaEnviar: Lodging = {
      descripcion: this.nuevoHospedaje.descripcion.trim(),
      imagen: this.nuevoHospedaje.imagen.trim(),
      precio_por_noche: Number(this.nuevoHospedaje.precio_por_noche),
      id_ciudad: this.nuevoHospedaje.id_ciudad,
      id_tipo_hospedaje: this.nuevoHospedaje.id_tipo_hospedaje,
      servicios: this.nuevoHospedaje.servicios
    };
  
    console.log('Crear hospedaje: datos enviados', hospedajeParaEnviar);
    
    this.lodgingService.crearHospedaje(hospedajeParaEnviar).subscribe({
      next: (hospedajeCreado) => {
        console.log('Hospedaje creado exitosamente', hospedajeCreado);
        this.misHospedajes.push(hospedajeCreado);
        this.mostrarFormularioCrear = false;
        this.resetearFormulario();
      },
      error: (error) => {
        console.error('Error al crear hospedaje', error);
        // Opcional: Mostrar mensaje de error al usuario
        this.mostrarMensajeError(error);
      }
    });
  }
  
  // Método de validación
  validarDatosHospedaje(): boolean {
    // Validar descripción
    if (!this.nuevoHospedaje.descripcion || this.nuevoHospedaje.descripcion.trim().length < 3) {
      this.mostrarMensajeError('La descripción debe tener al menos 3 caracteres');
      return false;
    }
    // Validar precio
    if (this.nuevoHospedaje.precio_por_noche <= 0) {
      this.mostrarMensajeError('El precio debe ser mayor a 0');
      return false;
    }
  
    // Validar ciudad
    if (!this.nuevoHospedaje.id_ciudad || this.nuevoHospedaje.id_ciudad === 0) {
      this.mostrarMensajeError('Seleccione una ciudad');
      return false;
    }
  
    // Validar tipo de hospedaje
    if (!this.nuevoHospedaje.id_tipo_hospedaje || this.nuevoHospedaje.id_tipo_hospedaje === 0) {
      this.mostrarMensajeError('Seleccione un tipo de hospedaje');
      return false;
    }
  
    // Validar servicios
    if (!this.nuevoHospedaje.servicios || this.nuevoHospedaje.servicios.length === 0) {
      this.mostrarMensajeError('Seleccione al menos un servicio');
      return false;
    }
  
    return true;
  }

  mostrarMensajeError(mensaje: string): void {
    alert(mensaje);
  }
  

  editarHospedaje(hospedaje: Lodging): void {
    // Clonar el hospedaje para evitar modificaciones directas
    this.hospedajeAEditar = { ...hospedaje };
    console.log(this.hospedajeAEditar);
    // Cargar ciudades del país de la ciudad del hospedaje
    // if (hospedaje.id_ciudad) {
    //   // Primero, obtener el país de la ciudad
    //   this.ciudadService.getPaises().subscribe({
    //     next: (paises) => {
    //       // Encontrar el país correcto
    //       const paisDelHospedaje = paises.find(pais => 
    //         pais.id === this.ciudadService.getPaisDeCiudad(hospedaje.id_ciudad!)
    //       );
          
    //       if (paisDelHospedaje) {
    //         // Cargar ciudades del país
    //         this.cargarCiudades(paisDelHospedaje.id!);
    //       }
    //     },
    //     error: (error) => console.error('Error al obtener países', error)
    //   });
    // }
    
    // Preparar el nuevoHospedaje con los datos para editar
    this.nuevoHospedaje = {
      id: hospedaje.id,
      descripcion: hospedaje.descripcion,
      imagen: hospedaje.imagen,
      precio_por_noche: hospedaje.precio_por_noche,
      id_ciudad: hospedaje.id_ciudad,
      id_tipo_hospedaje: hospedaje.id_tipo_hospedaje,
      servicios: hospedaje.servicios ? [...hospedaje.servicios] : []
    };
    
    // Abrir formulario de edición
    this.mostrarFormularioEditar = true;
  }

  guardarHospedajeEditado(): void {
    // Validaciones antes de editar
    if (!this.validarDatosHospedaje()) {
      return;
    }

    // Asegurarse de que tenemos un ID para editar
    if (!this.nuevoHospedaje.id) {
      this.mostrarMensajeError('Error: No se puede identificar el hospedaje a editar');
      return;
    }

    // Preparar el objeto para enviar (similar a crearHospedaje)
    const hospedajeParaEditar: Lodging = {
      descripcion: this.nuevoHospedaje.descripcion.trim(),
      imagen: this.nuevoHospedaje.imagen.trim(),
      precio_por_noche: Number(this.nuevoHospedaje.precio_por_noche),
      id_ciudad: this.nuevoHospedaje.id_ciudad,
      id_tipo_hospedaje: this.nuevoHospedaje.id_tipo_hospedaje,
      servicios: this.nuevoHospedaje.servicios
    };

    console.log('Editar hospedaje: datos enviados', hospedajeParaEditar);
    
    // Llamar al servicio para modificar el hospedaje
    this.lodgingService.modificarHospedaje(this.nuevoHospedaje.id, hospedajeParaEditar).subscribe({
      next: (hospedajeModificado) => {
        console.log('Hospedaje modificado exitosamente', hospedajeModificado);
        
        // Actualizar la lista de hospedajes
        const index = this.misHospedajes.findIndex(h => h.id === this.nuevoHospedaje.id);
        if (index !== -1) {
          // Reemplazar el hospedaje en la lista
          this.misHospedajes[index] = { ...hospedajeModificado, id: this.nuevoHospedaje.id };
        }
        
        // Cerrar formulario y resetear
        this.mostrarFormularioEditar = false;
        this.resetearFormulario();
      },
      error: (error) => {
        console.error('Error al modificar hospedaje', error);
        this.mostrarMensajeError(error);
      }
    });
  }

  isServiceSelected(servicioId?: number): boolean {
    // Only check if servicioId is defined
    if (servicioId === undefined) {
      return false;
    }
    return this.nuevoHospedaje.servicios.some(s => s.id === servicioId);
  }

  cancelarEdicion(): void {
    this.mostrarFormularioEditar = false;
    this.resetearFormulario();
  }

  // Método para resetear el formulario
  resetearFormulario(): void {
    this.nuevoHospedaje = {
      descripcion: '',
      imagen: '',
      precio_por_noche: 0,
      id_ciudad: 0,
      id_tipo_hospedaje: 0,
      servicios: [],
    };
    this.hospedajeAEditar = null;
  }

  eliminarHospedaje(hospedaje: Lodging): void {
    console.log('Eliminar hospedaje:', hospedaje);
  }
}
