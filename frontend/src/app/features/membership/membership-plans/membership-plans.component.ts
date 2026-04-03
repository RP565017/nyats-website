import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../../../core/services/api.service';
import { AuthService } from '../../../core/auth/auth.service';
import { NotificationService } from '../../../core/services/notification.service';

@Component({
  selector: 'app-membership-plans',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="membership-page">
      <div class="page-header">
        <div class="container">
          <h1>Membership Plans</h1>
          <p>Join our community and enjoy exclusive benefits</p>
        </div>
      </div>
      <div class="container">
        <div class="plans-grid">
          @for (plan of plans(); track plan.type) {
            <div class="plan-card" [class.popular]="plan.type === 'FAMILY'">
              @if (plan.type === 'FAMILY') { <div class="popular-badge">Most Popular</div> }
              <h3>{{ plan.name }}</h3>
              <div class="price"><span class="amount">${{ plan.price }}</span><span class="period">/year</span></div>
              <p>{{ plan.description }}</p>
              <button class="btn-join" (click)="joinPlan(plan)">Join Now</button>
            </div>
          }
        </div>
      </div>
    </div>
  `,
  styles: [`
    .membership-page {}
    .page-header { background: linear-gradient(135deg, #1a237e, #3949ab); color: white; padding: 3rem 1rem; margin-bottom: 3rem; h1 { font-size: 2rem; margin-bottom: 0.5rem; } p { opacity: 0.85; } }
    .container { max-width: 1200px; margin: 0 auto; padding: 0 1rem; }
    .plans-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 1.5rem; margin-bottom: 3rem; }
    .plan-card { background: white; border-radius: 16px; padding: 2rem; text-align: center; box-shadow: 0 4px 16px rgba(0,0,0,0.08); position: relative; border: 2px solid transparent; transition: all 0.2s; &:hover { border-color: #1a237e; transform: translateY(-4px); } &.popular { border-color: #f57c00; } }
    .popular-badge { position: absolute; top: -12px; left: 50%; transform: translateX(-50%); background: #f57c00; color: white; padding: 0.25rem 1rem; border-radius: 20px; font-size: 0.8rem; font-weight: 600; white-space: nowrap; }
    h3 { color: #1a237e; font-size: 1.25rem; margin-bottom: 1rem; }
    .price { margin: 1rem 0; .amount { font-size: 2.5rem; font-weight: 700; color: #1a237e; } .period { color: #999; } }
    p { color: #666; font-size: 0.9rem; margin-bottom: 1.5rem; line-height: 1.5; }
    .btn-join { width: 100%; padding: 0.75rem; background: #1a237e; color: white; border: none; border-radius: 8px; font-size: 1rem; font-weight: 600; cursor: pointer; transition: background 0.2s; &:hover { background: #283593; } }
  `]
})
export class MembershipPlansComponent implements OnInit {
  plans = signal<any[]>([]);

  constructor(private api: ApiService, public authService: AuthService, private router: Router, private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.api.get<any>('/memberships/plans').subscribe(res => this.plans.set(res.data ?? []));
  }

  joinPlan(plan: any): void {
    if (!this.authService.isAuthenticated()) {
      this.notificationService.info('Please login to join a membership');
      this.router.navigate(['/auth/login']);
      return;
    }
    this.api.post<any>('/memberships/subscribe', { type: plan.type }).subscribe({
      next: () => this.notificationService.success('Membership activated!'),
      error: () => {}
    });
  }
}
