import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, RegistrationData, TipoUsuario } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registrationForm!: FormGroup;
  isSubmitting = false;
  errorMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registrationForm = this.createRegistrationForm();
  }

  createRegistrationForm(): FormGroup {
    return this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      passwordRepeat: ['', Validators.required],
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      apellido: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      dni: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      fecha_nacimiento: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  private passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const passwordRepeat = control.get('passwordRepeat');

    if (password && passwordRepeat && password.value !== passwordRepeat.value) {
      return { passwordMismatch: true };
    }
    return null;
  }

  onSubmit(): void {
    if (this.registrationForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;
      this.errorMessage = '';

      const { passwordRepeat, ...formData } = this.registrationForm.value;

      const tipoUsuarios: TipoUsuario[] = [
        { id: 2, nombre: 'Inquilino' },
        { id: 3, nombre: 'Anfitrión' }
      ];
      
      const registrationData: RegistrationData = {
        ...formData,
        tipoUsuarios
      };

      this.authService.register(registrationData).subscribe({
        next: (response) => {
          console.log('Registration successful:', response);
          // Mostrar mensaje de éxito si lo deseas
          alert('Usuario registrado exitosamente');
          this.router.navigate(['/login']);
        },
        error: (error) => {
          console.error('Registration error:', error);
          this.errorMessage = error.message || 'Error en el registro';
          this.isSubmitting = false;
        },
        complete: () => {
          this.isSubmitting = false;
        }
      });
    }
  }

  get f() {
    return this.registrationForm.controls;
  }
}