import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/auth/auth.service';
import { NotificationService } from '../../../core/services/notification.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  template: `
    <div class="auth-page">
      <div class="auth-card">
        <h1>Create Account</h1>
        <p class="subtitle">Join the NY Albany Tamil Sangam community</p>
        <form [formGroup]="form" (ngSubmit)="submit()">
          <div class="fields-row">
            <div class="field">
              <label>First Name</label>
              <input type="text" formControlName="firstName" placeholder="First name">
            </div>
            <div class="field">
              <label>Last Name</label>
              <input type="text" formControlName="lastName" placeholder="Last name">
            </div>
          </div>
          <div class="field">
            <label>Email</label>
            <input type="email" formControlName="email" placeholder="you@example.com">
          </div>
          <div class="field">
            <label>Password</label>
            <input type="password" formControlName="password" placeholder="Min 8 characters">
          </div>
          <div class="field">
            <label>Phone (optional)</label>
            <input type="tel" formControlName="phone" placeholder="Your phone number">
          </div>
          <button type="submit" class="btn-submit" [disabled]="loading() || form.invalid">
            {{ loading() ? 'Creating account...' : 'Create Account' }}
          </button>
        </form>
        <p class="switch-link">Already have an account? <a routerLink="/auth/login">Sign in</a></p>
      </div>
    </div>
  `,
  styles: [`
    .auth-page { min-height: calc(100vh - 128px); display: flex; align-items: center; justify-content: center; background: #f5f5f5; padding: 2rem 1rem; }
    .auth-card { background: white; border-radius: 16px; padding: 2.5rem; width: 100%; max-width: 480px; box-shadow: 0 4px 24px rgba(0,0,0,0.1); }
    h1 { color: #1a237e; font-size: 1.75rem; margin-bottom: 0.5rem; }
    .subtitle { color: #666; margin-bottom: 2rem; }
    .fields-row { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
    .field { margin-bottom: 1.25rem; label { display: block; font-weight: 600; margin-bottom: 0.375rem; color: #333; font-size: 0.9rem; } input { width: 100%; padding: 0.75rem 1rem; border: 1.5px solid #ddd; border-radius: 8px; font-size: 1rem; box-sizing: border-box; &:focus { outline: none; border-color: #1a237e; } } }
    .btn-submit { width: 100%; padding: 0.875rem; background: #1a237e; color: white; border: none; border-radius: 8px; font-size: 1rem; font-weight: 600; cursor: pointer; margin-top: 0.5rem; &:hover:not([disabled]) { background: #283593; } &[disabled] { opacity: 0.6; cursor: not-allowed; } }
    .switch-link { text-align: center; margin-top: 1.5rem; color: #666; font-size: 0.9rem; a { color: #1a237e; font-weight: 600; text-decoration: none; } }
  `]
})
export class RegisterComponent {
  loading = signal(false);
  form = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    phone: ['']
  });

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private notificationService: NotificationService) {}

  submit(): void {
    if (this.form.invalid) return;
    this.loading.set(true);
    this.authService.register(this.form.value as any).subscribe({
      next: () => { this.router.navigate(['/']); this.notificationService.success('Welcome to NYATS!'); },
      error: () => this.loading.set(false)
    });
  }
}
