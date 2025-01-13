import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ FormsModule ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
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
}