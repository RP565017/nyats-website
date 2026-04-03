import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../../core/services/api.service';

@Component({
  selector: 'app-view-profile',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="profile-page container">
      <h1>My Profile</h1>
      @if (profile()) {
        <div class="profile-card">
          <div class="avatar">{{ profile().firstName.charAt(0) }}{{ profile().lastName.charAt(0) }}</div>
          <div class="profile-info">
            <h2>{{ profile().firstName }} {{ profile().lastName }}</h2>
            <p>{{ profile().email }}</p>
            <span class="role-badge" [class]="profile().role.toLowerCase()">{{ profile().role }}</span>
            @if (profile().phone) { <p>📱 {{ profile().phone }}</p> }
            @if (profile().address) { <p>📍 {{ profile().address }}</p> }
            <p class="member-since">Member since: {{ profile().createdAt | date:'longDate' }}</p>
          </div>
        </div>
      } @else {
        <p class="loading">Loading profile...</p>
      }
    </div>
  `,
  styles: [`
    .profile-page { max-width: 600px; margin: 0 auto; padding: 2rem 1rem; }
    h1 { color: #1a237e; margin-bottom: 2rem; }
    .profile-card { background: white; border-radius: 16px; padding: 2rem; box-shadow: 0 4px 16px rgba(0,0,0,0.08); display: flex; gap: 2rem; align-items: flex-start; @media (max-width: 480px) { flex-direction: column; } }
    .avatar { width: 80px; height: 80px; border-radius: 50%; background: #1a237e; color: white; display: flex; align-items: center; justify-content: center; font-size: 1.75rem; font-weight: 700; flex-shrink: 0; }
    .profile-info { flex: 1; h2 { color: #1a237e; margin-bottom: 0.5rem; } p { color: #555; margin: 0.4rem 0; } }
    .role-badge { display: inline-block; padding: 0.25rem 0.75rem; border-radius: 20px; font-size: 0.8rem; font-weight: 600; &.user { background: #e3f2fd; color: #1565c0; } &.member { background: #e8f5e9; color: #2e7d32; } &.admin { background: #fce4ec; color: #c62828; } }
    .member-since { font-size: 0.85rem; color: #999; margin-top: 1rem !important; }
    .loading { text-align: center; padding: 3rem; color: #666; }
  `]
})
export class ViewProfileComponent implements OnInit {
  profile = signal<any>(null);
  constructor(private api: ApiService) {}
  ngOnInit(): void {
    this.api.get<any>('/users/me').subscribe(res => this.profile.set(res.data));
  }
}
