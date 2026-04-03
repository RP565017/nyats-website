import { Routes } from '@angular/router';

export const ANNOUNCEMENTS_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./announcement-list/announcement-list.component').then(m => m.AnnouncementListComponent)
  }
];
