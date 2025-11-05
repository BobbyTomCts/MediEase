import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class Login {
  email: string = '';
  password: string = '';
  message: string = '';
  isError: boolean = false;

  constructor(private authService: Auth, private router: Router) {}

  // Handle login
  onLogin(): void {
    console.log('Login attempt with:', this.email);
    
    this.authService.login(this.email, this.password).subscribe({
      next: (response) => {
        console.log('Login response:', response);
        console.log('Token:', response.token);
        console.log('Role:', response.role);
        console.log('IsAdmin:', response.admin);
        
        if (response.token) {
          // Save all user data (token, id, name, email, role, admin)
          this.authService.saveUserData(response);
          
          console.log('Saved to localStorage');
          
          this.message = 'Login successful! Redirecting...';
          this.isError = false;

          // Redirect based on admin flag
          setTimeout(() => {
            console.log('Redirecting... admin:', response.admin);
            if (response.admin) {
              console.log('Navigating to admin-dashboard');
              this.router.navigate(['/admin-dashboard']);
            } else {
              console.log('Navigating to user-dashboard');
              this.router.navigate(['/user-dashboard']);
            }
          }, 1500);
        } else {
          this.message = response.message || 'Login failed';
          this.isError = true;
          console.log('No token in response');
        }
      },
      error: (error) => {
        this.message = 'Invalid email or password';
        this.isError = true;
        console.error('Login error:', error);
      }
    });
  }

  // Navigate to register page
  goToRegister(): void {
    this.router.navigate(['/register']);
  }
}
