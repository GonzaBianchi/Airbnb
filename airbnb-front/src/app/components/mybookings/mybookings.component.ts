import { Component, OnInit } from '@angular/core';
import { BookingService, BookingResponse, BookingRequest } from '../../services/booking.service';

@Component({
  selector: 'app-mybookings',
  templateUrl: './mybookings.component.html',
  styleUrls: ['./mybookings.component.css']
})
export class MybookingsComponent implements OnInit {
  bookings: BookingResponse[] = [];
  loading = true;
  error: string | null = null;
  editingBooking: BookingResponse | null = null;

  constructor(private bookingService: BookingService) {}

  ngOnInit() {
    this.cargarReservas();
  }

  cargarReservas() {
    this.loading = true;
    this.bookingService.getReservasByUser().subscribe({
      next: (reservas) => {
        console.log('Cargando reservas:', reservas);
        this.bookings = reservas.map(reserva => ({
          ...reserva,
          cant_ninos: reserva.cant_ninos ?? 0,
          cant_bebes: reserva.cant_bebes ?? 0,
          cant_mascotas: reserva.cant_mascotas ?? 0
        }));
        this.loading = false;
      },
      error: (err) => {
        this.error = err.error || 'Error al cargar las reservas';
        this.loading = false;
      }
    });
  }

  modificarReserva(reserva: BookingResponse) {
    this.editingBooking = { 
      ...reserva,
      cant_ninos: reserva.cant_ninos ?? 0,
      cant_bebes: reserva.cant_bebes ?? 0,
      cant_mascotas: reserva.cant_mascotas ?? 0
    };
  }

  guardarModificacion() {
    if (!this.editingBooking) return;
  
    const modificacionReserva: BookingRequest = {
      id_hospedaje: this.editingBooking.hospedaje.id,
      fecha_check_in: this.editingBooking.fecha_check_in!,
      fecha_check_out: this.editingBooking.fecha_check_out!,
      cant_ninos: this.editingBooking.cant_ninos ?? 0,
      cant_adultos: this.editingBooking.cant_adultos!,
      cant_bebes: this.editingBooking.cant_bebes ?? 0,
      cant_mascotas: this.editingBooking.cant_mascotas ?? 0
    };

    console.log('Modificando reserva:', modificacionReserva);
    this.bookingService.modificarReserva(this.editingBooking.id, modificacionReserva).subscribe({
      next: () => {
        alert('Reserva modificada exitosamente');
        this.cargarReservas();
        this.editingBooking = null;
      },
      error: (err) => {
        alert('Error al modificar la reserva');
        console.error(err);
      }
    });
  }

  cancelarEdicion() {
    this.editingBooking = null;
  }

  cancelarReserva(idReserva: number) {
    const confirmacion = confirm('¿Estás seguro de que deseas cancelar esta reserva?');
    
    if (confirmacion) {
      this.bookingService.cancelarReserva(idReserva).subscribe({
        next: () => {
          alert('Reserva cancelada exitosamente');
          this.cargarReservas();
        },
        error: (err) => {
          alert('Error al cancelar la reserva');
          console.error(err);
        }
      });
    }
  }
}