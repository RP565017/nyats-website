import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../../core/services/api.service';
import { PaginationComponent } from '../../../shared/components/pagination/pagination.component';

@Component({
  selector: 'app-announcement-list',
  standalone: true,
  imports: [CommonModule, PaginationComponent],
  template: `
    <div class="announcements-page">
      <div class="page-header"><div class="container"><h1>Announcements</h1><p>Stay up to date with the latest news</p></div></div>
      <div class="container">
        <div class="announcements-list">
          @for (ann of announcements(); track ann.id) {
            <div class="announcement-card" [class.pinned]="ann.pinned">
              @if (ann.pinned) { <span class="pin-badge">📌 Pinned</span> }
              <h3>{{ ann.title }}</h3>
              <p>{{ ann.content }}</p>
              <span class="date">{{ ann.createdAt | date:'longDate' }}</span>
            </div>
          }
          @if (announcements().length === 0) {
            <p class="no-content">No announcements at this time.</p>
          }
        </div>
        <app-pagination [currentPage]="currentPage()" [totalPages]="totalPages()" (pageChange)="load($event)"></app-pagination>
      </div>
    </div>
  `,
  styles: [`
    .announcements-page {}
    .page-header { background: linear-gradient(135deg, #1a237e, #3949ab); color: white; padding: 3rem 1rem; margin-bottom: 2rem; h1 { font-size: 2rem; margin-bottom: 0.5rem; } p { opacity: 0.85; } }
    .container { max-width: 800px; margin: 0 auto; padding: 0 1rem; }
    .announcements-list { display: flex; flex-direction: column; gap: 1rem; margin-bottom: 2rem; }
    .announcement-card { background: white; border-radius: 12px; padding: 1.5rem; box-shadow: 0 2px 8px rgba(0,0,0,0.06); border-left: 4px solid #1a237e; &.pinned { border-left-color: #f57c00; } }
    .pin-badge { font-size: 0.8rem; font-weight: 600; color: #f57c00; display: block; margin-bottom: 0.5rem; }
    h3 { color: #1a237e; margin-bottom: 0.75rem; }
    p { color: #555; line-height: 1.7; margin-bottom: 0.75rem; }
    .date { font-size: 0.85rem; color: #999; }
    .no-content { text-align: center; color: #999; padding: 3rem; }
  `]
})
export class AnnouncementListComponent implements OnInit {
  announcements = signal<any[]>([]);
  totalPages = signal(0);
  currentPage = signal(0);

  constructor(private api: ApiService) {}
  ngOnInit(): void { this.load(0); }
  load(page: number): void {
    this.api.get<any>('/announcements', { page, size: 10 }).subscribe(res => {
      this.announcements.set(res.data?.content ?? []);
      this.totalPages.set(res.data?.totalPages ?? 0);
      this.currentPage.set(page);
    });
  }
}
