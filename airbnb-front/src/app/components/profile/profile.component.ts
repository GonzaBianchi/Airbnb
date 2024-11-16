import { Component, OnInit } from '@angular/core';
import { UserService, EditarUsuarioDTO } from 'src/app/services/user.service';
import { Usuario } from '../../services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  usuario: Usuario | null = null;
  error: string = '';
  success: string = '';
  loading: boolean = false;
  editForm: FormGroup;
  editMode: boolean = false;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private fb: FormBuilder
  ) {
    this.editForm = this.fb.group({
      username: ['', Validators.required],
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      apellido: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      dni: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      password: [''],
      fecha_nacimiento: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.mostrarUsuario();
  }

  mostrarUsuario() {
    this.loading = true;
    this.error = '';
    this.success = '';
    
    this.userService.obtenerUsuario().subscribe({
      next: (data) => {
        this.usuario = data;
        this.editForm.patchValue({
          username: data.username,
          nombre: data.nombre,
          apellido: data.apellido,
          email: data.email,
          dni: data.dni,
          fecha_nacimiento: data.fecha_nacimiento,
          password: '' // Limpiamos el campo de contraseña
        });
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al obtener usuario:', error);
        this.error = error.message || 'Error al cargar los datos del usuario';
        this.loading = false;
      }
    });
  }

  toggleEditMode() {
    this.editMode = !this.editMode;
    if (!this.editMode) {
      // Si cancelamos la edición, restauramos los valores originales
      this.mostrarUsuario();
    }
    this.error = '';
    this.success = '';
  }

  onSubmit() {
    if (this.editForm.valid) {
      this.loading = true;
      this.error = '';
      this.success = '';

      const formValue = this.editForm.value;
      const usuarioEditado: EditarUsuarioDTO = {
        ...formValue,
        password: formValue.password || undefined
      };

      this.userService.modificarUsuario(usuarioEditado).subscribe({
        next: () => {
          this.success = 'Usuario modificado exitosamente';
          this.loading = false;
          this.editMode = false;
          this.mostrarUsuario();
        },
        error: (error) => {
          console.error('Error detallado:', error);
          // El error ahora viene directamente como string
          this.error = typeof error === 'string' ? error : 'Error al modificar los datos del usuario';
          this.loading = false;
        }
      });
    } else {
      this.error = 'Por favor, completa correctamente todos los campos requeridos';
    }
  }
}