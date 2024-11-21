import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LodgingResponse, LodgingService } from '../../services/lodging.service';

@Component({
  selector: 'app-lodgingview',
  templateUrl: './lodgingview.component.html',
  styleUrls: ['./lodgingview.component.css']
})
export class LodgingviewComponent implements OnInit{
  hospedaje!: LodgingResponse;

  constructor(
    private route: ActivatedRoute,
    private lodgingService: LodgingService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      const id = params['id'];
      this.getHospedajeDetails(id);
    });
  }

  getHospedajeDetails(id: number) {
    this.lodgingService.getHospedajeUnico(id).subscribe({
      next: (hospedaje) => (
        console.log('Fetched hospedaje details:', hospedaje),
        this.hospedaje = hospedaje
      ),
      error: (error) => (
        console.error('Error fetching hospedaje details:', error)
      )
    });
  }

  reservaHospedaje(hospedajeId: number) {
    this.router.navigate(['/reservas', hospedajeId]);
  }
}