import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService, BookingRequest } from '../../services/booking.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {
  bookingForm!: FormGroup;
  hospedajeId!: number;

  constructor(
    private route: ActivatedRoute,
    private bookingService: BookingService,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.hospedajeId = params['id'];
    });
    
    this.bookingForm = this.fb.group({
      fecha_check_in: ['', Validators.required],
      fecha_check_out: ['', Validators.required],
      cant_adultos: [1, [Validators.required, Validators.min(1)]],
      cant_ninos: [0, Validators.min(0)],
      cant_bebes: [0, Validators.min(0)],
      cant_mascotas: [0, Validators.min(0)]
    });
  }

  onSubmit() {
    if (this.bookingForm.valid) {
      const bookingRequest: BookingRequest = {
        ...this.bookingForm.value,
        id_hospedaje: this.hospedajeId
      };

      this.bookingService.crearReserva(bookingRequest).subscribe({
        next: () => (
          alert('Reserva realiada con éxito'),
          this.router.navigate(['/mis-reservas']) 
        ),
        error: (error) => {
          console.error('Error al crear reserva', error);
          alert("Error al finalizar la reserva, " + error)
        }
      });
    }
  }
}
