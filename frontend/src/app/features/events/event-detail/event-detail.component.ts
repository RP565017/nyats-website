import { Component, OnInit, Input, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { EventService } from '../services/event.service';
import { AuthService } from '../../../core/auth/auth.service';
import { NotificationService } from '../../../core/services/notification.service';

@Component({
  selector: 'app-event-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent implements OnInit {
  @Input() slug!: string;
  event = signal<any>(null);
  loading = signal(true);
  registering = signal(false);

  constructor(
    private eventService: EventService,
    public authService: AuthService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.eventService.getEventBySlug(this.slug).subscribe({
      next: res => { this.event.set(res.data); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  register(): void {
    if (!this.authService.isAuthenticated()) {
      this.notificationService.info('Please login to register for events');
      return;
    }
    this.registering.set(true);
    this.eventService.registerForEvent(this.event()!.id).subscribe({
      next: () => { this.notificationService.success('Successfully registered!'); this.registering.set(false); },
      error: () => this.registering.set(false)
    });
  }
}
