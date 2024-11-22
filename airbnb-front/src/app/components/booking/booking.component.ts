import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService, BookingRequest } from '../../services/booking.service';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(20px)' }),
        animate('0.5s ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ]),
    trigger('buttonAnimation', [
      transition(':enter', [
        style({ opacity: 0, transform: 'scale(0.8)' }),
        animate('0.3s ease-out', style({ opacity: 1, transform: 'scale(1)' }))
      ])
    ])
  ]
})
export class BookingComponent implements OnInit {
  bookingForm!: FormGroup;
  hospedajeId!: number;
  isSubmitting = false;
  minCheckInDate: string;
  minCheckOutDate: string;

  constructor(
    private route: ActivatedRoute,
    private bookingService: BookingService,
    private router: Router,
    private fb: FormBuilder
  ) {
    const today = new Date();
    const minCheckIn = new Date(today);
    minCheckIn.setDate(today.getDate() + 2);
    this.minCheckInDate = this.formatDate(minCheckIn);

    const minCheckOut = new Date(minCheckIn);
    minCheckOut.setDate(minCheckIn.getDate() + 1);
    this.minCheckOutDate = this.formatDate(minCheckOut);

    this.bookingForm = this.fb.group({
      fecha_check_in: ['', [Validators.required, this.checkInDateValidator()]],
      fecha_check_out: ['', [Validators.required]],
      cant_adultos: [1, [Validators.required, Validators.min(1)]],
      cant_ninos: [0, [Validators.required, Validators.min(0)]],
      cant_bebes: [0, [Validators.required, Validators.min(0)]],
      cant_mascotas: [0, [Validators.required, Validators.min(0)]]
    }, { validators: this.dateRangeValidator });
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.hospedajeId = params['id'];
    });
  }

  private formatDate(date: Date): string {
    return date.toISOString().split('T')[0];
  }

  private checkInDateValidator() {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const selectedDate = new Date(control.value);
      const minDate = new Date(this.minCheckInDate);

      if (selectedDate < minDate) {
        return { minCheckInDate: true };
      }

      return null;
    };
  }

  private dateRangeValidator(group: AbstractControl): ValidationErrors | null {
    const checkIn = group.get('fecha_check_in')?.value;
    const checkOut = group.get('fecha_check_out')?.value;

    if (!checkIn || !checkOut) {
      return null;
    }

    const checkInDate = new Date(checkIn);
    const checkOutDate = new Date(checkOut);

    if (checkOutDate <= checkInDate) {
      return { invalidDateRange: true };
    }

    return null;
  }

  updateCheckoutMin() {
    const checkInDate = this.bookingForm.get('fecha_check_in')?.value;
    if (checkInDate) {
      const nextDay = new Date(checkInDate);
      nextDay.setDate(nextDay.getDate() + 1);
      this.minCheckOutDate = this.formatDate(nextDay);
      
      const currentCheckOut = this.bookingForm.get('fecha_check_out')?.value;
      if (currentCheckOut && new Date(currentCheckOut) <= new Date(checkInDate)) {
        this.bookingForm.patchValue({
          fecha_check_out: this.minCheckOutDate
        });
      }
    }
  }

  onSubmit() {
    if (this.bookingForm.valid) {
      this.isSubmitting = true;
      const bookingRequest: BookingRequest = {
        ...this.bookingForm.value,
        id_hospedaje: this.hospedajeId
      };

      this.bookingService.crearReserva(bookingRequest).subscribe({
        next: () => {
          alert('Reserva realizada con éxito');
          this.router.navigate(['/mis-reservas']);
        },
        error: (error) => {
          console.error('Error al crear reserva', error.message);
          alert(error.message);
          this.isSubmitting = false;
        }
      });
    }
  }
}