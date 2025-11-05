import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Interface for User
export interface User {
  id?: number;
  name: string;
  phone: string;
  email: string;
  password: string;
  role?: string;
}

// Interface for Login Response
export interface LoginResponse {
  id: number;
  name: string;
  email: string;
  role: string;
  admin: boolean;  // Changed from isAdmin to admin (JSON property name)
  message: string;
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class Auth {
  // Backend API URL
  private apiUrl = 'http://localhost:8082/api/users';

  constructor(private http: HttpClient) { }

  // Register new user
  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, user);
  }

  // Login user
  login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login?email=${email}&password=${password}`, {});
  }

  // Validate token and get user details
  validateToken(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/validate`);
  }

  // Save user data and token to localStorage
  saveUserData(response: LoginResponse): void {
    localStorage.setItem('token', response.token);
    localStorage.setItem('userId', response.id.toString());
    localStorage.setItem('userName', response.name);
    localStorage.setItem('userEmail', response.email);
    localStorage.setItem('userRole', response.role);
    localStorage.setItem('isAdmin', response.admin.toString());
  }

  // Save additional user details from validate endpoint
  saveUserDetails(user: User): void {
    if (user.name) localStorage.setItem('userName', user.name);
    if (user.email) localStorage.setItem('userEmail', user.email);
  }

  // Get token from localStorage
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Check if user is logged in
  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // Check if user is admin
  isAdmin(): boolean {
    return localStorage.getItem('isAdmin') === 'true';
  }

  // Get user name
  getUserName(): string | null {
    return localStorage.getItem('userName');
  }

  // Logout user
  logout(): void {
    localStorage.clear();
  }
}
