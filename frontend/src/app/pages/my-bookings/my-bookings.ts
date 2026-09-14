import { Component, inject, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HotelApi } from '../../hotel-api';
import { Booking } from '../../models';
import { apiErrorMessage } from '../../dates';

@Component({
  selector: 'app-my-bookings',
  imports: [CurrencyPipe, FormsModule],
  templateUrl: './my-bookings.html',
  styleUrl: './my-bookings.scss',
})
export class MyBookings {
  private readonly api = inject(HotelApi);

  protected readonly bookings = signal<Booking[]>([]);
  protected readonly searched = signal(false);
  protected readonly error = signal('');
  protected email = '';

  constructor() {
    const email = inject(ActivatedRoute).snapshot.queryParamMap.get('email');
    if (email) {
      this.email = email;
      this.load();
    }
  }

  protected submit(event: Event): void {
    event.preventDefault();
    this.load();
  }

  protected cancel(booking: Booking): void {
    this.api.cancel(booking.reference).subscribe({
      next: () => this.load(),
      error: (error) => this.error.set(apiErrorMessage(error, 'Could not cancel that booking.')),
    });
  }

  private load(): void {
    this.error.set('');
    this.api.bookingsByEmail(this.email).subscribe({
      next: (bookings) => {
        this.bookings.set(bookings);
        this.searched.set(true);
      },
      error: (error) => this.error.set(apiErrorMessage(error, 'Could not load your bookings.')),
    });
  }
}
