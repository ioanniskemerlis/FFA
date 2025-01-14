import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-register',
    imports: [
        FormsModule,
        MatToolbarModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
    ],
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerData = { username: '', email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  onRegister() {
    this.authService.register(this.registerData).subscribe({
      next: (response: any) => {
        console.log('Registration successful:', response.message); // Log success message
        alert(response.message); // Show success message to the user
        this.router.navigate(['/auth']); // Redirect to login page
      },
      error: (error) => {
        console.error('Registration failed:', error);
        alert('Registration failed! Please try again.');
      },
    });
  }
  navigateToLogin() {
    this.router.navigate(['/auth']); // Redirect to the login page
  }
}