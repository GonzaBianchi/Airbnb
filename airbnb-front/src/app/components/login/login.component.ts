import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage: string = '';
  loading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    // Si necesitas inicializar algo al cargar el componente
  }

  // Getter para acceder fácilmente a los controles del formulario
  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        if (response.token) {
          // Aquí puedes guardar el token en localStorage o donde prefieras
          localStorage.setItem('token', response.token);
          this.router.navigate(['/']); // Navega a la ruta que desees después del login
        }
      },
      error: (error) => {
        this.errorMessage = error.message || 'Error al iniciar sesión';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}