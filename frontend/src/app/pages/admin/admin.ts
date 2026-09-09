import { Component, computed, inject, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { HotelApi } from '../../hotel-api';
import { Booking } from '../../models';
import { apiErrorMessage } from '../../dates';

@Component({
  selector: 'app-admin',
  imports: [CurrencyPipe],
  templateUrl: './admin.html',
  styleUrl: './admin.scss',
})
export class Admin {
  private readonly api = inject(HotelApi);

  protected readonly bookings = signal<Booking[]>([]);
  protected readonly error = signal('');

  protected readonly confirmed = computed(() => this.bookings().filter((booking) => booking.status === 'CONFIRMED'));
  protected readonly revenue = computed(() =>
    this.confirmed().reduce((total, booking) => total + booking.totalPrice, 0),
  );
  protected readonly guests = computed(() =>
    this.confirmed().reduce((total, booking) => total + booking.guests, 0),
  );

  constructor() {
    this.load();
  }

  protected cancel(booking: Booking): void {
    this.api.cancel(booking.reference).subscribe({
      next: () => this.load(),
      error: (error) => this.error.set(apiErrorMessage(error, 'Could not cancel that booking.')),
    });
  }

  protected load(): void {
    this.error.set('');
    this.api.allBookings().subscribe({
      next: (bookings) => this.bookings.set(bookings),
      error: (error) => this.error.set(apiErrorMessage(error, 'Could not load bookings.')),
    });
  }
}
