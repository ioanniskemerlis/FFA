import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule, // Add ReactiveFormsModule for form control
    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup; // Define the form group

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Initialize the form group with validators
    this.registerForm = this.fb.group({
      username: [
        '',
        [
          Validators.required,
          Validators.pattern('^[a-zA-Z0-9_]{5,20}$'), // Username validation
        ],
      ],
      email: [
        '',
        [
          Validators.required,
          Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$'), // Email validation
        ],
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.pattern(
            '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$'
          ), // Password validation
        ],
      ],
    });
  }
  backendError: string | null = null;
  onRegister(): void {
    if (this.registerForm.invalid) {
      this.backendError = null; // Clear previous errors
      alert('Please fill out the form correctly.');
      return;
    }

    const registerData = this.registerForm.value;

    this.authService.register(registerData).subscribe({
      next: (response: any) => {
        console.log('Registration successful:', response.message);
        alert(response.message);
        this.router.navigate(['/auth']);
      },
      error: (error) => {
        console.error('Registration failed:', error);
        // Update backendError with the error message
      if (error.status === 400 && error.error.message === 'Username already exists!') {
        this.backendError = 'This username is already taken. Please choose another one.';
      } else {
        this.backendError = 'Registration failed! Please try again.';
      }
      },
    });
  }

  navigateToLogin(): void {
    this.router.navigate(['/auth']);
  }
}
