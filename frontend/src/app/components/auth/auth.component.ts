import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [ FormsModule ],
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
})
export class AuthComponent {
  loginData = { username: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  // Define the onLogin method
  onLogin() {
    this.authService.login(this.loginData).subscribe({
      next: (response: any) => {
        console.log('Login successful, token:', response.token); // Log the received token
        localStorage.setItem('token', response.token); // Save the token
        this.router.navigate(['/dashboard']); // Redirect to the dashboard
      },
      error: (error) => {
        console.error('Login failed:', error);
        alert('Invalid username or password. Please try again.');
      },
      complete: () => {
        console.log('Login request completed.');
      },
    });
  }
}
