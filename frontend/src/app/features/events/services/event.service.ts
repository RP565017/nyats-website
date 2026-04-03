import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';

@Injectable({ providedIn: 'root' })
export class EventService {
  constructor(private api: ApiService) {}

  getEvents(page = 0, size = 10, type?: string, search?: string): Observable<any> {
    return this.api.get('/events', { page, size, type, search });
  }

  getEventBySlug(slug: string): Observable<any> {
    return this.api.get(`/events/${slug}`);
  }

  registerForEvent(eventId: string, attendeeCount = 1): Observable<any> {
    return this.api.post(`/events/${eventId}/register`, { attendeeCount });
  }
}
