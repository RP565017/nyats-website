import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <div class="admin-layout">
      <aside class="sidebar">
        <div class="sidebar-brand">
          <h2>NYATS Admin</h2>
        </div>
        <nav class="sidebar-nav">
          <a routerLink="/admin/dashboard" routerLinkActive="active">Dashboard</a>
          <a routerLink="/admin/events" routerLinkActive="active">Events</a>
          <a routerLink="/admin/members" routerLinkActive="active">Members</a>
          <a routerLink="/admin/gallery" routerLinkActive="active">Gallery</a>
          <a routerLink="/admin/announcements" routerLinkActive="active">Announcements</a>
        </nav>
        <button class="btn-logout" (click)="logout()">Logout</button>
      </aside>
      <main class="admin-content">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    .admin-layout { display: flex; min-height: 100vh; }
    .sidebar { width: 240px; background: #1a237e; color: white; padding: 1rem; display: flex; flex-direction: column; }
    .sidebar-brand h2 { color: white; font-size: 1.2rem; margin-bottom: 2rem; }
    .sidebar-nav { display: flex; flex-direction: column; gap: 0.5rem; flex: 1; }
    .sidebar-nav a { color: rgba(255,255,255,0.8); padding: 0.75rem 1rem; border-radius: 6px; text-decoration: none; transition: all 0.2s; }
    .sidebar-nav a:hover, .sidebar-nav a.active { background: rgba(255,255,255,0.15); color: white; }
    .admin-content { flex: 1; padding: 2rem; background: #f5f5f5; overflow-y: auto; }
    .btn-logout { background: rgba(255,255,255,0.1); color: white; border: 1px solid rgba(255,255,255,0.3); padding: 0.5rem 1rem; border-radius: 6px; cursor: pointer; }
  `]
})
export class AdminLayoutComponent {
  constructor(private authService: AuthService) {}
  logout() { this.authService.logout(); }
}
