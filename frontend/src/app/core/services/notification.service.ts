import { Injectable, signal } from '@angular/core';

export interface Notification {
  id: string;
  type: 'success' | 'error' | 'info' | 'warning';
  message: string;
}

@Injectable({ providedIn: 'root' })
export class NotificationService {
  readonly notifications = signal<Notification[]>([]);

  success(message: string): void {
    this.addNotification('success', message);
  }

  error(message: string): void {
    this.addNotification('error', message);
  }

  info(message: string): void {
    this.addNotification('info', message);
  }

  warning(message: string): void {
    this.addNotification('warning', message);
  }

  dismiss(id: string): void {
    this.notifications.update(list => list.filter(n => n.id !== id));
  }

  private addNotification(type: Notification['type'], message: string): void {
    const id = Math.random().toString(36).substr(2, 9);
    this.notifications.update(list => [...list, { id, type, message }]);
    setTimeout(() => this.dismiss(id), 5000);
  }
}
