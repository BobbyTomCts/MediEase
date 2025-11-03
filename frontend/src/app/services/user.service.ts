import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// User model
export interface UserProfile {
  id: number;
  name: string;
  phone: string;
  email: string;
  password: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8082/api/users';

  constructor(private http: HttpClient) {}

  // Get user by ID
  getUserById(userId: number): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/${userId}`);
  }

  // Update user profile
  updateUser(userId: number, user: Partial<UserProfile>): Observable<UserProfile> {
    return this.http.put<UserProfile>(`${this.apiUrl}/${userId}`, user);
  }

  // Check if user is admin (verify with backend)
  isAdmin(userId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/isAdmin/${userId}`);
  }
}
