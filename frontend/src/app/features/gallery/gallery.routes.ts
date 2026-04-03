import { Routes } from '@angular/router';

export const GALLERY_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./album-list/album-list.component').then(m => m.AlbumListComponent)
  },
  {
    path: ':slug',
    loadComponent: () => import('./album-detail/album-detail.component').then(m => m.AlbumDetailComponent)
  }
];
