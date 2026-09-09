import { Component, inject, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HotelApi } from '../../hotel-api';
import { Booking, BookingRequest, Room } from '../../models';
import { apiErrorMessage, daysFromNow, nightsBetween, today } from '../../dates';

@Component({
  selector: 'app-book',
  imports: [CurrencyPipe, FormsModule, RouterLink],
  templateUrl: './book.html',
  styleUrl: './book.scss',
})
export class Book {
  private readonly api = inject(HotelApi);
  private readonly route = inject(ActivatedRoute);

  protected readonly room = signal<Room | null>(null);
  protected readonly confirmation = signal<Booking | null>(null);
  protected readonly error = signal('');
  protected readonly submitting = signal(false);
  protected readonly minDate = today();

  protected form: BookingRequest = {
    roomId: 0,
    guestName: '',
    guestEmail: '',
    guestPhone: '',
    checkIn: daysFromNow(7),
    checkOut: daysFromNow(9),
    guests: 2,
  };

  constructor() {
    const params = this.route.snapshot.queryParamMap;
    this.form.checkIn = params.get('checkIn') || this.form.checkIn;
    this.form.checkOut = params.get('checkOut') || this.form.checkOut;
    this.form.guests = Number(params.get('guests')) || this.form.guests;
    this.form.roomId = Number(this.route.snapshot.paramMap.get('id'));

    this.api.getRoom(this.form.roomId).subscribe({
      next: (room) => this.room.set(room),
      error: (error) => this.error.set(apiErrorMessage(error, 'Room not found')),
    });
  }

  protected get nights(): number {
    return nightsBetween(this.form.checkIn, this.form.checkOut);
  }

  protected get total(): number {
    return this.nights * (this.room()?.pricePerNight ?? 0);
  }

  protected submit(event: Event): void {
    event.preventDefault();
    this.submitting.set(true);
    this.error.set('');
    this.api.book({ ...this.form, guests: +this.form.guests }).subscribe({
      next: (booking) => {
        this.confirmation.set(booking);
        this.submitting.set(false);
      },
      error: (error) => {
        this.error.set(apiErrorMessage(error, 'We could not complete this booking.'));
        this.submitting.set(false);
      },
    });
  }
}
