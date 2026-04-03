import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  upcomingEvents = signal<any[]>([]);
  announcements = signal<any[]>([]);

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.get<any>('/events', { page: 0, size: 3 }).subscribe(res => {
      this.upcomingEvents.set(res.data?.content ?? []);
    });
    this.apiService.get<any>('/announcements', { page: 0, size: 3 }).subscribe(res => {
      this.announcements.set(res.data?.content ?? []);
    });
  }
}
