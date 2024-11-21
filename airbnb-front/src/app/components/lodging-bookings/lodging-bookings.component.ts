import { Component, OnInit } from '@angular/core';
import { BookingService, BookingResponse, BookingRequest } from '../../services/booking.service';

@Component({
  selector: 'app-lodging-bookings',
  templateUrl: './lodging-bookings.component.html',
  styleUrls: ['./lodging-bookings.component.css']
})
export class LodgingBookingsComponent implements OnInit {
  bookings: BookingResponse[] = [];
  loading = true;
  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.cargarReservas();
  }

  cargarReservas() {
    this.loading = true;
    this.bookingService.getReservasAnfitrion().subscribe({
      next: (reservas) => {
        console.log('Cargando reservas:', reservas),
        this.bookings = reservas;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar reservas', err);
        this.loading = false;
      }
    });
  }

  cancelarReserva(idReserva: number) {
    this.bookingService.cancelarReserva(idReserva).subscribe({
      next: () => (
        alert('Reserva cancelada exitosamente'),
        this.cargarReservas()
      ),
      error: (err) => (
        console.log('Error al cancelar la reserva', err),
        alert('Error al cancelar la reserva')
      )
    });
  }

  confirmarReserva(idReserva: number) {
    this.bookingService.confirmarReserva(idReserva).subscribe({
      next: () => (
        alert('Reserva confirmada exitosamente'),
        this.cargarReservas()

      ),
      error: (err) => (
        console.log('Error al confirmar la reserva', err),
        alert('Error al confirmar la reserva')
      )
    });
  }

}