import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../../core/services/api.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="dashboard">
      <h1>Admin Dashboard</h1>
      <div class="stats-grid">
        <div class="stat-card">
          <span class="stat-icon">👥</span>
          <div class="stat-info">
            <div class="stat-value">{{ stats()?.totalUsers ?? '—' }}</div>
            <div class="stat-label">Total Users</div>
          </div>
        </div>
        <div class="stat-card">
          <span class="stat-icon">🎭</span>
          <div class="stat-info">
            <div class="stat-value">{{ stats()?.totalEvents ?? '—' }}</div>
            <div class="stat-label">Total Events</div>
          </div>
        </div>
        <div class="stat-card">
          <span class="stat-icon">✅</span>
          <div class="stat-info">
            <div class="stat-value">{{ stats()?.activeMembers ?? '—' }}</div>
            <div class="stat-label">Active Members</div>
          </div>
        </div>
        <div class="stat-card">
          <span class="stat-icon">📢</span>
          <div class="stat-info">
            <div class="stat-value">{{ stats()?.totalAnnouncements ?? '—' }}</div>
            <div class="stat-label">Announcements</div>
          </div>
        </div>
      </div>
      <div class="quick-actions">
        <h2>Quick Actions</h2>
        <div class="actions-grid">
          <a routerLink="/admin/events" class="action-btn">Manage Events</a>
          <a routerLink="/admin/members" class="action-btn">Manage Members</a>
          <a routerLink="/admin/gallery" class="action-btn">Manage Gallery</a>
          <a routerLink="/admin/announcements" class="action-btn">Manage Announcements</a>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .dashboard { }
    h1 { color: #1a237e; margin-bottom: 2rem; font-size: 1.75rem; }
    .stats-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 1.5rem; margin-bottom: 3rem; }
    .stat-card { background: white; border-radius: 12px; padding: 1.5rem; box-shadow: 0 2px 8px rgba(0,0,0,0.06); display: flex; gap: 1rem; align-items: center; .stat-icon { font-size: 2rem; } .stat-value { font-size: 2rem; font-weight: 700; color: #1a237e; } .stat-label { color: #666; font-size: 0.9rem; } }
    h2 { color: #1a237e; margin-bottom: 1rem; }
    .actions-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(160px, 1fr)); gap: 1rem; }
    .action-btn { display: block; padding: 1rem; background: white; color: #1a237e; border-radius: 8px; text-decoration: none; text-align: center; font-weight: 600; box-shadow: 0 2px 8px rgba(0,0,0,0.06); transition: all 0.2s; &:hover { background: #1a237e; color: white; } }
  `]
})
export class DashboardComponent implements OnInit {
  stats = signal<any>(null);
  constructor(private api: ApiService) {}
  ngOnInit(): void {
    this.api.get<any>('/admin/dashboard').subscribe(res => this.stats.set(res.data));
  }
}
