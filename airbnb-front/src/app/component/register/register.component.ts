import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { AuthService, RegistrationData } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registrationForm: FormGroup

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {
    this.registrationForm = this.createRegistrationForm();
  }

  createRegistrationForm(): FormGroup {
    return this.formBuilder.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      passwordRepeat: new FormControl('', Validators.required),
      nombre: new FormControl('', Validators.required),
      apellido: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      dni: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{8}$')]),
      fecha_nacimiento: new FormControl('', Validators.required)
    }, {
      validators: this.passwordMatchValidator
    });
  }

  passwordMatchValidator(form: FormGroup): any {
    const password = form.get('password');
    const passwordRepeat = form.get('passwordRepeat');

    if (password && passwordRepeat && password.value !== passwordRepeat.value) {
      return { passwordMismatch: true };
    } else {
      return null;
    }
  }

  onSubmit() {
    if (this.registrationForm.valid) {
      const formData: RegistrationData = {
        ...this.registrationForm.value,
        tipoUsuarios: [
          { id: 2 },
          { id: 3 }
        ]
      };

      this.authService.register(formData)
        .subscribe({
          next: (response) => {
            console.log('Registration successful:', response);
          },
          error: (error) => {
            console.error('Registration error:', error);
          }
        });
    }
  }
}