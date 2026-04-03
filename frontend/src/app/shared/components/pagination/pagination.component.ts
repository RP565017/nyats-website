import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="pagination">
      <button [disabled]="currentPage === 0" (click)="changePage(currentPage - 1)">&#8249;</button>
      @for (page of pages; track page) {
        <button [class.active]="page === currentPage" (click)="changePage(page)">{{ page + 1 }}</button>
      }
      <button [disabled]="currentPage === totalPages - 1" (click)="changePage(currentPage + 1)">&#8250;</button>
    </div>
  `,
  styles: [`
    .pagination { display: flex; gap: 0.5rem; justify-content: center; padding: 1rem 0; }
    button { padding: 0.5rem 0.75rem; border: 1px solid #ddd; background: white; border-radius: 6px; cursor: pointer; transition: all 0.2s; }
    button:hover:not([disabled]) { background: #1a237e; color: white; border-color: #1a237e; }
    button.active { background: #1a237e; color: white; border-color: #1a237e; }
    button[disabled] { opacity: 0.4; cursor: not-allowed; }
  `]
})
export class PaginationComponent {
  @Input() currentPage = 0;
  @Input() totalPages = 0;
  @Output() pageChange = new EventEmitter<number>();

  get pages(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

  changePage(page: number): void {
    if (page >= 0 && page < this.totalPages) this.pageChange.emit(page);
  }
}
