import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="contact-page">
      <div class="page-header"><div class="container"><h1>Contact Us</h1><p>Get in touch with the NY Albany Tamil Sangam</p></div></div>
      <div class="container">
        <div class="contact-grid">
          <div class="contact-info">
            <h2>Get in Touch</h2>
            <div class="info-item"><span class="icon">📍</span><div><strong>Address</strong><p>Albany, New York</p></div></div>
            <div class="info-item"><span class="icon">✉️</span><div><strong>Email</strong><p>info&#64;nyalbanytamilsangam.org</p></div></div>
            <div class="info-item"><span class="icon">🌐</span><div><strong>Website</strong><p>nyalbanytamilsangam.org</p></div></div>
          </div>
          <div class="contact-form-wrapper">
            <h2>Send a Message</h2>
            <form [formGroup]="form" (ngSubmit)="submit()">
              <div class="field"><label>Name</label><input type="text" formControlName="name" placeholder="Your name"></div>
              <div class="field"><label>Email</label><input type="email" formControlName="email" placeholder="your@email.com"></div>
              <div class="field"><label>Subject</label><input type="text" formControlName="subject" placeholder="Message subject"></div>
              <div class="field"><label>Message</label><textarea formControlName="message" rows="5" placeholder="Your message..."></textarea></div>
              <button type="submit" [disabled]="form.invalid || sent()" class="btn-send">
                {{ sent() ? 'Message Sent!' : 'Send Message' }}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .contact-page {}
    .page-header { background: linear-gradient(135deg, #1a237e, #3949ab); color: white; padding: 3rem 1rem; margin-bottom: 2rem; h1 { font-size: 2rem; margin-bottom: 0.5rem; } p { opacity: 0.85; } }
    .container { max-width: 900px; margin: 0 auto; padding: 0 1rem; }
    .contact-grid { display: grid; grid-template-columns: 1fr 1.5fr; gap: 3rem; @media (max-width: 768px) { grid-template-columns: 1fr; } }
    h2 { color: #1a237e; margin-bottom: 1.5rem; }
    .info-item { display: flex; gap: 1rem; margin-bottom: 1.5rem; align-items: flex-start; .icon { font-size: 1.5rem; } strong { display: block; color: #333; margin-bottom: 0.25rem; } p { color: #666; margin: 0; } }
    .field { margin-bottom: 1.25rem; label { display: block; font-weight: 600; margin-bottom: 0.375rem; font-size: 0.9rem; } input, textarea { width: 100%; padding: 0.75rem 1rem; border: 1.5px solid #ddd; border-radius: 8px; font-size: 1rem; box-sizing: border-box; font-family: inherit; &:focus { outline: none; border-color: #1a237e; } } textarea { resize: vertical; } }
    .btn-send { width: 100%; padding: 0.875rem; background: #1a237e; color: white; border: none; border-radius: 8px; font-size: 1rem; font-weight: 600; cursor: pointer; &:hover:not([disabled]) { background: #283593; } &[disabled] { opacity: 0.6; cursor: not-allowed; } }
  `]
})
export class ContactComponent {
  sent = signal(false);
  form = this.fb.group({
    name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    subject: ['', Validators.required],
    message: ['', [Validators.required, Validators.minLength(10)]]
  });

  constructor(private fb: FormBuilder, private notificationService: NotificationService) {}

  submit(): void {
    if (this.form.invalid) return;
    this.sent.set(true);
    this.notificationService.success('Message sent! We will get back to you soon.');
  }
}
