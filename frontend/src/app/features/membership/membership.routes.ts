import { Routes } from '@angular/router';

export const MEMBERSHIP_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./membership-plans/membership-plans.component').then(m => m.MembershipPlansComponent)
  }
];
