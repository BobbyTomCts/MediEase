import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

/**
 * HTTP Interceptor that automatically adds JWT token to all requests
 * and handles authentication errors
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  
  // Get token from localStorage
  const token = localStorage.getItem('token');
  
  // Skip adding token for login and register endpoints
  const isPublicEndpoint = req.url.includes('/login') || req.url.includes('/register');
  
  // Clone request and add Authorization header if token exists and not public endpoint
  let authReq = req;
  if (token && !isPublicEndpoint) {
    authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }
  
  // Handle the request and catch errors
  return next(authReq).pipe(
    catchError((error) => {
      // If 401 Unauthorized, clear token and redirect to login
      if (error.status === 401) {
        console.error('Unauthorized access - redirecting to login');
        localStorage.clear();
        router.navigate(['/login']);
      }
      
      // If 403 Forbidden
      if (error.status === 403) {
        console.error('Access forbidden');
      }
      
      return throwError(() => error);
    })
  );
};
