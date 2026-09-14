import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/home/home').then((m) => m.Home),
    title: 'Azure Bay Hotel',
  },
  {
    path: 'rooms',
    loadComponent: () => import('./pages/rooms/rooms').then((m) => m.Rooms),
    title: 'Rooms & suites',
  },
  {
    path: 'book/:id',
    loadComponent: () => import('./pages/book/book').then((m) => m.Book),
    title: 'Book your stay',
  },
  {
    path: 'bookings',
    loadComponent: () => import('./pages/my-bookings/my-bookings').then((m) => m.MyBookings),
    title: 'My bookings',
  },
  {
    path: 'admin',
    loadComponent: () => import('./pages/admin/admin').then((m) => m.Admin),
    title: 'Admin',
  },
  { path: '**', redirectTo: '' },
];
