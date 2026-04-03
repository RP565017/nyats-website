import { Component, OnInit, Input, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../../core/services/api.service';

@Component({
  selector: 'app-album-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="album-detail container">
      <div class="back-link"><a routerLink="/gallery">← Back to Gallery</a></div>
      @if (album()) {
        <h1>{{ album().title }}</h1>
        <p class="description">{{ album().description }}</p>
        <div class="photos-grid">
          @for (item of album().items; track item.publicId) {
            <div class="photo-item">
              <img [src]="item.thumbnailUrl || item.url" [alt]="item.caption || 'Photo'" loading="lazy">
              @if (item.caption) { <p class="caption">{{ item.caption }}</p> }
            </div>
          }
        </div>
      } @else {
        <p class="loading">Loading...</p>
      }
    </div>
  `,
  styles: [`
    .album-detail { max-width: 1200px; margin: 0 auto; padding: 2rem 1rem; }
    .back-link { margin-bottom: 1.5rem; a { color: #1a237e; text-decoration: none; } }
    h1 { color: #1a237e; margin-bottom: 1rem; }
    .description { color: #666; margin-bottom: 2rem; }
    .photos-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 1rem; }
    .photo-item { border-radius: 8px; overflow: hidden; img { width: 100%; height: 200px; object-fit: cover; transition: transform 0.2s; &:hover { transform: scale(1.05); } } }
    .caption { text-align: center; font-size: 0.85rem; color: #666; padding: 0.5rem; }
    .loading { text-align: center; padding: 3rem; color: #666; }
  `]
})
export class AlbumDetailComponent implements OnInit {
  @Input() slug!: string;
  album = signal<any>(null);

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.api.get<any>(`/gallery/${this.slug}`).subscribe(res => this.album.set(res.data));
  }
}
