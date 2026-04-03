import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EventService } from '../services/event.service';
import { PaginationComponent } from '../../../shared/components/pagination/pagination.component';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, PaginationComponent],
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss']
})
export class EventListComponent implements OnInit {
  events = signal<any[]>([]);
  totalPages = signal(0);
  currentPage = signal(0);
  loading = signal(false);
  searchQuery = '';
  selectedType = '';

  constructor(private eventService: EventService) {}

  ngOnInit(): void { this.loadEvents(); }

  loadEvents(page = 0): void {
    this.loading.set(true);
    this.eventService.getEvents(page, 9, this.selectedType || undefined, this.searchQuery || undefined).subscribe({
      next: res => {
        this.events.set(res.data?.content ?? []);
        this.totalPages.set(res.data?.totalPages ?? 0);
        this.currentPage.set(page);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  search(): void { this.loadEvents(0); }
  onPageChange(page: number): void { this.loadEvents(page); }
}
