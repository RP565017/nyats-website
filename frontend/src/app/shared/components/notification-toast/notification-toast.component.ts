import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from '../../../core/services/notification.service';

@Component({
  selector: 'app-notification-toast',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="toast-container">
      @for (n of notificationService.notifications(); track n.id) {
        <div class="toast" [class]="'toast-' + n.type">
          <span>{{ n.message }}</span>
          <button (click)="notificationService.dismiss(n.id)">×</button>
        </div>
      }
    </div>
  `,
  styles: [`
    .toast-container { position: fixed; top: 80px; right: 1rem; z-index: 9999; display: flex; flex-direction: column; gap: 0.5rem; max-width: 350px; }
    .toast { display: flex; align-items: center; justify-content: space-between; padding: 0.75rem 1rem; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); animation: slideIn 0.3s ease; gap: 1rem; }
    .toast-success { background: #e8f5e9; color: #2e7d32; border-left: 4px solid #4caf50; }
    .toast-error { background: #ffebee; color: #c62828; border-left: 4px solid #f44336; }
    .toast-info { background: #e3f2fd; color: #1565c0; border-left: 4px solid #2196f3; }
    .toast-warning { background: #fff8e1; color: #f57f17; border-left: 4px solid #ff9800; }
    button { background: none; border: none; cursor: pointer; font-size: 1.2rem; opacity: 0.7; }
    @keyframes slideIn { from { transform: translateX(100%); opacity: 0; } to { transform: translateX(0); opacity: 1; } }
  `]
})
export class NotificationToastComponent {
  constructor(public notificationService: NotificationService) {}
}
