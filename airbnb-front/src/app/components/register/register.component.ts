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
      username: ['', [
        Validators.required, 
        Validators.minLength(3),
        Validators.pattern('^[a-zA-Z0-9_-]{3,16}$')
      ]],
      password: ['', [
        Validators.required, 
        Validators.minLength(8),
        Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$')
      ]],
      passwordRepeat: ['', Validators.required],
      nombre: ['', [
        Validators.required, 
        Validators.minLength(2),
        Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$')
      ]],
      apellido: ['', [
        Validators.required, 
        Validators.minLength(2),
        Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$')
      ]],
      email: ['', [
        Validators.required, 
        Validators.email,
        Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')
      ]],
      dni: ['', [
        Validators.required, 
        Validators.pattern('^[0-9]{8}$')
      ]],
      fecha_nacimiento: ['', [
        Validators.required,
        this.edadMinimaValidator(18)
      ]]
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

  private edadMinimaValidator(edadMinima: number) {
    return (control: AbstractControl): {[key: string]: any} | null => {
      if (!control.value) return null;
      
      const fechaNacimiento = new Date(control.value);
      const hoy = new Date();
      let edad = hoy.getFullYear() - fechaNacimiento.getFullYear();
      const m = hoy.getMonth() - fechaNacimiento.getMonth();
      
      if (m < 0 || (m === 0 && hoy.getDate() < fechaNacimiento.getDate())) {
        edad--;
      }
      
      return edad >= edadMinima ? null : { edadMinima: true };
    };
  }

  onSubmit(): void {
    this.registrationForm.markAllAsTouched();
    
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
          alert('Registro exitoso');
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