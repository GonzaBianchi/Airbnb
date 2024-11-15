// profile.component.ts
import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Usuario } from '../../services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  usuario: Usuario | null = null;
  error: string = '';
  loading: boolean = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.mostrarUsuario();
  }

  mostrarUsuario() {
    this.loading = true;
    this.userService.obtenerUsuario().subscribe({
      next: (data) => {
        console.log('Datos del usuario:', data);
        this.usuario = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al obtener usuario:', error);
        this.error = 'Error al cargar los datos del usuario';
        this.loading = false;
      }
    });
  }
}
