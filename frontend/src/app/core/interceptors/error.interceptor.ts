import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from '../services/notification.service';
import { AuthService } from '../auth/auth.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const notificationService = inject(NotificationService);
  const authService = inject(AuthService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        authService.logout();
        notificationService.error('Session expired. Please login again.');
      } else if (error.status === 403) {
        notificationService.error('Access denied.');
      } else if (error.status === 0) {
        notificationService.error('Unable to connect to server.');
      } else {
        const message = error.error?.message || 'An error occurred. Please try again.';
        notificationService.error(message);
      }
      return throwError(() => error);
    })
  );
};
