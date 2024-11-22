import { Component, OnInit } from '@angular/core';
import { BookingService, BookingResponse, BookingRequest } from '../../services/booking.service';
import { trigger, transition, style, animate } from '@angular/animations';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';

export const cardAnimation = trigger('cardAnimation', [
  transition(':enter', [
    style({ 
      opacity: 0,
      transform: 'translateY(20px)'
    }),
    animate('500ms ease-out', style({ 
      opacity: 1,
      transform: 'translateY(0)'
    }))
  ]),
  transition(':leave', [
    animate('500ms ease-in', style({ 
      opacity: 0,
      transform: 'translateY(20px)'
    }))
  ])
]);

@Component({
  selector: 'app-mybookings',
  templateUrl: './mybookings.component.html',
  styleUrls: ['./mybookings.component.css'],
  animations: [cardAnimation]
})
export class MybookingsComponent implements OnInit {
  bookings: BookingResponse[] = [];
  loading = true;
  error: string | null = null;
  editingBooking: BookingResponse | null = null;
  editForm!: FormGroup;
  validationErrors: string[] = [];

  constructor(private bookingService: BookingService, private fb: FormBuilder) {}

  ngOnInit() {
    this.cargarReservas();
  }

  cargarReservas() {
    this.loading = true;
    this.bookingService.getReservasByUser().subscribe({
      next: (reservas) => {
        console.log('Reservas recibidas:', reservas);
        this.bookings = reservas.map(reserva => ({
          ...reserva,
          cant_ninos: reserva.cant_ninos ?? 0,
          cant_bebes: reserva.cant_bebes ?? 0,
          cant_mascotas: reserva.cant_mascotas ?? 0
        }));
        console.log('Bookings procesados:', this.bookings);
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar reservas:', err);
        this.error = err.error || 'Error al cargar las reservas';
        this.loading = false;
      }
    });
  }

  modificarReserva(reserva: BookingResponse) {
    this.editingBooking = { ...reserva };
    this.initializeForm(reserva);
  }

  guardarModificacion() {
    if (!this.editingBooking || !this.validateForm()) {
      return;
    }

    const modificacionReserva: BookingRequest = {
      id_hospedaje: this.editingBooking.hospedaje.id,
      fecha_check_in: this.editForm.get('fecha_check_in')!.value,
      fecha_check_out: this.editForm.get('fecha_check_out')!.value,
      cant_adultos: this.editForm.get('cant_adultos')!.value,
      cant_ninos: this.editForm.get('cant_ninos')!.value,
      cant_bebes: this.editForm.get('cant_bebes')!.value,
      cant_mascotas: this.editForm.get('cant_mascotas')!.value
    };

    this.bookingService.modificarReserva(this.editingBooking.id, modificacionReserva)
      .subscribe({
        next: () => {
          alert('Reserva modificada exitosamente');
          this.cargarReservas();
          this.editingBooking = null;
          this.validationErrors = [];
        },
        error: (err) => {
          alert(err.message || 'Error al modificar la reserva');
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

  isBookingCancelled(booking: BookingResponse): boolean {
    return booking.estado?.toLowerCase() === 'cancelada';
  }

  getStatusClass(estado: string): string {
    switch (estado?.toLowerCase()) {
      case 'confirmada':
        return 'status-confirmed';
      case 'pendiente':
        return 'status-pending';
      case 'cancelada':
        return 'status-cancelled';
      default:
        return 'status-pending';
    }
  }

   // Método para inicializar el formulario
   initializeForm(booking: BookingResponse) {
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);

    this.editForm = this.fb.group({
      fecha_check_in: [booking.fecha_check_in, [
        Validators.required,
        this.minDateValidator(tomorrow)
      ]],
      fecha_check_out: [booking.fecha_check_out, [
        Validators.required,
        this.minDateValidator(tomorrow)
      ]],
      cant_adultos: [booking.cant_adultos, [
        Validators.required,
        Validators.min(1)
      ]],
      cant_ninos: [booking.cant_ninos || 0, [
        Validators.required,
        Validators.min(0)
      ]],
      cant_bebes: [booking.cant_bebes || 0, [
        Validators.required,
        Validators.min(0)
      ]],
      cant_mascotas: [booking.cant_mascotas || 0, [
        Validators.required,
        Validators.min(0)
      ]]
    }, { validators: this.checkInOutValidator });
  }

  minDateValidator(minDate: Date) {
    return (control: AbstractControl): ValidationErrors | null => {
      const inputDate = new Date(control.value);
      if (inputDate < minDate) {
        return { minDate: { min: minDate, actual: inputDate } };
      }
      return null;
    };
  }

  checkInOutValidator(group: FormGroup): ValidationErrors | null {
    const checkIn = new Date(group.get('fecha_check_in')?.value);
    const checkOut = new Date(group.get('fecha_check_out')?.value);

    if (checkIn >= checkOut) {
      return { checkInOutInvalid: true };
    }
    return null;
  }

  validateForm(): boolean {
    this.validationErrors = [];
    
    if (this.editForm.invalid) {
      if (this.editForm.errors?.['checkInOutInvalid']) {
        this.validationErrors.push('La fecha de check-out debe ser posterior a la fecha de check-in');
      }

      const checkInControl = this.editForm.get('fecha_check_in');
      const checkOutControl = this.editForm.get('fecha_check_out');
      const cantAdultosControl = this.editForm.get('cant_adultos');

      if (checkInControl?.errors?.['minDate']) {
        this.validationErrors.push('La fecha de check-in debe ser posterior a mañana');
      }
      if (checkOutControl?.errors?.['minDate']) {
        this.validationErrors.push('La fecha de check-out debe ser posterior a mañana');
      }
      if (cantAdultosControl?.errors?.['min']) {
        this.validationErrors.push('Debe haber al menos 1 adulto');
      }

      return false;
    }
    return true;
  }
}