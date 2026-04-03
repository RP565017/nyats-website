import { Routes } from '@angular/router';

export const PROFILE_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./view-profile/view-profile.component').then(m => m.ViewProfileComponent)
  }
];
