import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../../core/services/api.service';
import { PaginationComponent } from '../../../shared/components/pagination/pagination.component';

@Component({
  selector: 'app-album-list',
  standalone: true,
  imports: [CommonModule, RouterLink, PaginationComponent],
  template: `
    <div class="gallery-page">
      <div class="page-header"><div class="container"><h1>Photo Gallery</h1><p>Memories from our events and celebrations</p></div></div>
      <div class="container">
        <div class="gallery-grid">
          @for (album of albums(); track album.id) {
            <a [routerLink]="['/gallery', album.slug]" class="album-card">
              @if (album.coverImageUrl) {
                <img [src]="album.coverImageUrl" [alt]="album.title">
              } @else {
                <div class="album-placeholder">📸</div>
              }
              <div class="album-info">
                <h3>{{ album.title }}</h3>
                <p>{{ album.items?.length ?? 0 }} photos</p>
              </div>
            </a>
          }
          @if (albums().length === 0) {
            <p class="no-content">No albums yet. Check back soon!</p>
          }
        </div>
        <app-pagination [currentPage]="currentPage()" [totalPages]="totalPages()" (pageChange)="loadAlbums($event)"></app-pagination>
      </div>
    </div>
  `,
  styles: [`
    .gallery-page {}
    .page-header { background: linear-gradient(135deg, #1a237e, #3949ab); color: white; padding: 3rem 1rem; margin-bottom: 2rem; h1 { font-size: 2rem; margin-bottom: 0.5rem; } p { opacity: 0.85; } }
    .container { max-width: 1200px; margin: 0 auto; padding: 0 1rem; }
    .gallery-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 1.5rem; margin-bottom: 2rem; }
    .album-card { text-decoration: none; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.08); transition: transform 0.2s; display: block; background: white; &:hover { transform: translateY(-4px); } img { width: 100%; height: 220px; object-fit: cover; } }
    .album-placeholder { height: 220px; background: linear-gradient(135deg, #e3f2fd, #bbdefb); display: flex; align-items: center; justify-content: center; font-size: 3rem; }
    .album-info { padding: 1rem; h3 { color: #1a237e; margin-bottom: 0.25rem; } p { color: #999; font-size: 0.875rem; } }
    .no-content { grid-column: 1/-1; text-align: center; color: #999; padding: 3rem; }
  `]
})
export class AlbumListComponent implements OnInit {
  albums = signal<any[]>([]);
  totalPages = signal(0);
  currentPage = signal(0);

  constructor(private api: ApiService) {}

  ngOnInit(): void { this.loadAlbums(0); }

  loadAlbums(page: number): void {
    this.api.get<any>('/gallery', { page, size: 12 }).subscribe(res => {
      this.albums.set(res.data?.content ?? []);
      this.totalPages.set(res.data?.totalPages ?? 0);
      this.currentPage.set(page);
    });
  }
}
