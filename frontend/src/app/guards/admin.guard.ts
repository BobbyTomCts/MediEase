import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Auth } from '../services/auth';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(
    private authService: Auth,
    private userService: UserService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    // First check if user is logged in
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return of(false);
    }

    // Get user ID from localStorage
    const userIdStr = localStorage.getItem('userId');
    if (!userIdStr) {
      this.router.navigate(['/login']);
      return of(false);
    }

    const userId = parseInt(userIdStr);

    // Validate admin status with backend
    return this.userService.isAdmin(userId).pipe(
      map(isAdmin => {
        if (isAdmin) {
          return true;
        } else {
          // User is not admin, redirect to user dashboard
          this.router.navigate(['/user-dashboard']);
          return false;
        }
      }),
      catchError(error => {
        console.error('Error validating admin status:', error);
        this.router.navigate(['/login']);
        return of(false);
      })
    );
  }
}
